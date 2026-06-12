package com.kadiri.elkitabi.features.kitap.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.features.kitap.domain.model.KADIRI_KITAPLARI
import com.kadiri.elkitabi.features.kitap.domain.model.Kitap
import com.kadiri.elkitabi.features.kitap.domain.model.KitapBolum
import com.kadiri.elkitabi.features.kitap.domain.model.YerImi
import com.kadiri.elkitabi.features.kitap.domain.repository.KitapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class KitapListUiState(
    val kitaplar: List<Kitap> = KADIRI_KITAPLARI,
    val isLoading: Boolean    = false
)

data class KitapDetayUiState(
    val kitap: Kitap?          = null,
    val bolumler: List<KitapBolum> = emptyList(),
    val yerImleri: List<YerImi>    = emptyList(),
    val secilenBolum: KitapBolum?  = null,
    val fontBoyutu: Float          = 16f,
    val isArabicMode: Boolean      = false,
    val isLoading: Boolean         = true
)

@HiltViewModel
class KitapViewModel @Inject constructor(
    private val kitapRepository: KitapRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listState  = MutableStateFlow(KitapListUiState())
    val listState: StateFlow<KitapListUiState> = _listState.asStateFlow()

    private val _detayState = MutableStateFlow(KitapDetayUiState())
    val detayState: StateFlow<KitapDetayUiState> = _detayState.asStateFlow()

    fun loadKitap(kitapId: String) {
        viewModelScope.launch {
            kitapRepository.seedBolumlerIfEmpty(kitapId)
            val (okunan, toplam) = kitapRepository.getKitapIlerlemesi(kitapId)
            val kitap = KADIRI_KITAPLARI.find { it.id == kitapId }?.copy(
                toplamBolum  = toplam,
                okunmusBolum = okunan
            )
            _detayState.update { it.copy(kitap = kitap) }

            kitapRepository.getBolumler(kitapId).onEach { bolumler ->
                _detayState.update { s ->
                    s.copy(
                        bolumler  = bolumler,
                        isLoading = false,
                        secilenBolum = s.secilenBolum ?: bolumler.firstOrNull()
                    )
                }
            }.launchIn(viewModelScope)

            kitapRepository.getYerImleri(kitapId).onEach { liste ->
                _detayState.update { it.copy(yerImleri = liste) }
            }.launchIn(viewModelScope)
        }
    }

    fun bolumSec(bolum: KitapBolum) {
        viewModelScope.launch {
            kitapRepository.markBolumAsRead(bolum.id)
            _detayState.update { it.copy(secilenBolum = bolum) }
        }
    }

    fun fontBoyutuDegistir(boyut: Float) =
        _detayState.update { it.copy(fontBoyutu = boyut) }

    fun arabicModToggle() =
        _detayState.update { it.copy(isArabicMode = !it.isArabicMode) }

    fun yerImiEkle(kitapId: String, bolumId: Long, sayfaNo: Int, not: String = "") {
        viewModelScope.launch {
            kitapRepository.addYerImi(YerImi(kitapId = kitapId, bolumId = bolumId, sayfaNo = sayfaNo, not = not))
        }
    }

    fun yerImiSil(yerImiId: Long) {
        viewModelScope.launch { kitapRepository.deleteYerImi(yerImiId) }
    }
}
