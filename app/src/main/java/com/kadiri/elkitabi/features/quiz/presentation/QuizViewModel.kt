package com.kadiri.elkitabi.features.quiz.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.features.quiz.data.local.QuizDao
import com.kadiri.elkitabi.features.quiz.data.local.entities.QuizSkorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.time.LocalDate
import javax.inject.Inject

@Serializable
data class KategoriItem(val id: String, val isim: String, val ikon: String, val renk: String)

@Serializable
data class SoruItem(
    val id: Int,
    val kategori: String,
    val soru: String,
    val secenekler: List<String>,
    val dogruCevap: Int,
    val aciklama: String,
    val zorluk: String
)

@Serializable
private data class QuizJson(val kategoriler: List<KategoriItem>, val sorular: List<SoruItem>)

data class SkorUi(val kategori: String, val dogru: Int)

data class QuizUiState(
    val kategoriler: List<KategoriItem> = emptyList(),
    val topSkorlar: List<SkorUi> = emptyList(),
    val aktifKategori: String = "",
    val sorular: List<SoruItem> = emptyList(),
    val soruIndex: Int = 0,
    val aktifSoru: SoruItem? = null,
    val secilenCevap: Int? = null,
    val dogruSayisi: Int = 0,
    val toplamSoru: Int = 10,
    val bitti: Boolean = false
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val quizDao: QuizDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }
    private var tumSorular: List<SoruItem> = emptyList()

    init {
        viewModelScope.launch {
            try {
                val raw = context.assets.open("data/quiz.json").bufferedReader().readText()
                val data = json.decodeFromString<QuizJson>(raw)
                tumSorular = data.sorular
                quizDao.getAllSkorlar().collect { skorlar ->
                    _uiState.value = _uiState.value.copy(
                        kategoriler = data.kategoriler,
                        topSkorlar = skorlar.take(5).map { SkorUi(it.kategori, it.dogru) }
                    )
                }
            } catch (_: Exception) {}
        }
    }

    fun quizBaslat(kategori: String) {
        val kategoriBilgi = _uiState.value.kategoriler.find { it.id == kategori }
        val filtrelenmis = tumSorular.filter { it.kategori == kategori }.shuffled().take(10)
        _uiState.value = _uiState.value.copy(
            aktifKategori = kategoriBilgi?.isim ?: kategori,
            sorular = filtrelenmis,
            soruIndex = 0,
            aktifSoru = filtrelenmis.firstOrNull(),
            secilenCevap = null,
            dogruSayisi = 0,
            toplamSoru = filtrelenmis.size,
            bitti = false
        )
    }

    fun cevapSec(idx: Int) {
        val state = _uiState.value
        val soru = state.aktifSoru ?: return
        val dogru = if (idx == soru.dogruCevap) state.dogruSayisi + 1 else state.dogruSayisi
        _uiState.value = state.copy(secilenCevap = idx, dogruSayisi = dogru)
    }

    fun sonrakiSoru() {
        val state = _uiState.value
        val yeniIndex = state.soruIndex + 1
        if (yeniIndex >= state.sorular.size) {
            viewModelScope.launch {
                quizDao.insertSkor(
                    QuizSkorEntity(
                        kategori = state.aktifKategori,
                        dogru = state.dogruSayisi,
                        yanlis = state.toplamSoru - state.dogruSayisi,
                        toplamSure = 0,
                        tarih = LocalDate.now().toString()
                    )
                )
            }
            _uiState.value = state.copy(bitti = true, aktifSoru = null)
        } else {
            _uiState.value = state.copy(
                soruIndex = yeniIndex,
                aktifSoru = state.sorular[yeniIndex],
                secilenCevap = null
            )
        }
    }
}
