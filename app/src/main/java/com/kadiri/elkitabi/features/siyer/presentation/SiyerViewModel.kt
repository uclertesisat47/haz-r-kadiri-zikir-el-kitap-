package com.kadiri.elkitabi.features.siyer.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.features.siyer.data.local.SiyerDao
import com.kadiri.elkitabi.features.siyer.data.local.entities.SiyerFavoriEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.LocalDate
import javax.inject.Inject

@Serializable
data class SiyerKategori(val id: String, val isim: String, val renk: String)

@Serializable
data class SiyerOlay(
    val id: Int, val kategori: String, val miladi: Int, val hicri: String,
    val baslik: String, val ozet: String, val onem: String
)

@Serializable
private data class SiyerJson(val kategoriler: List<SiyerKategori>, val olaylar: List<SiyerOlay>)

data class SiyerOlayUi(
    val id: Int, val kategori: String, val kategoriRenk: String,
    val miladi: Int, val hicri: String, val baslik: String, val ozet: String
)

data class SiyerUiState(
    val kategoriler: List<SiyerKategori> = emptyList(),
    val tumOlaylar: List<SiyerOlayUi> = emptyList(),
    val filtreliOlaylar: List<SiyerOlayUi> = emptyList(),
    val favoriler: Set<Int> = emptySet(),
    val secilenKategori: String? = null
)

@HiltViewModel
class SiyerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val siyerDao: SiyerDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(SiyerUiState())
    val uiState: StateFlow<SiyerUiState> = _uiState.asStateFlow()
    private val json = Json { ignoreUnknownKeys = true }

    init {
        viewModelScope.launch {
            try {
                val raw = context.assets.open("data/siyer.json").bufferedReader().readText()
                val data = json.decodeFromString<SiyerJson>(raw)
                val katMap = data.kategoriler.associateBy { it.id }
                val olayUiList = data.olaylar.map {
                    SiyerOlayUi(
                        id = it.id, kategori = it.kategori,
                        kategoriRenk = katMap[it.kategori]?.renk ?: "#FFFFFF",
                        miladi = it.miladi, hicri = it.hicri,
                        baslik = it.baslik, ozet = it.ozet
                    )
                }
                combine(siyerDao.getAllFavoriler()) { favorilerArr ->
                    val favSet = favorilerArr[0].map { it.olayId }.toSet()
                    _uiState.value.copy(
                        kategoriler = data.kategoriler,
                        tumOlaylar = olayUiList,
                        filtreliOlaylar = olayUiList,
                        favoriler = favSet
                    )
                }.collect { _uiState.value = it }
            } catch (_: Exception) {}
        }
    }

    fun ara(metin: String) {
        val state = _uiState.value
        _uiState.value = state.copy(
            filtreliOlaylar = state.tumOlaylar.filter {
                metin.isBlank() || it.baslik.contains(metin, true) || it.miladi.toString().contains(metin)
            }
        )
    }

    fun kategoriFiltrele(kategori: String?) {
        val state = _uiState.value
        _uiState.value = state.copy(
            secilenKategori = kategori,
            filtreliOlaylar = if (kategori == null) state.tumOlaylar
                else state.tumOlaylar.filter { it.kategori == kategori }
        )
    }

    fun favoriToggle(olayId: Int) {
        viewModelScope.launch {
            if (siyerDao.isFavori(olayId)) {
                siyerDao.removeFavori(SiyerFavoriEntity(olayId, ""))
            } else {
                siyerDao.addFavori(SiyerFavoriEntity(olayId, LocalDate.now().toString()))
            }
        }
    }
}
