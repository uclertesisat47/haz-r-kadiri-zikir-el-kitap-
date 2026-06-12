package com.kadiri.elkitabi.features.kazanamaz.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.features.kazanamaz.data.local.KazaNamaziDao
import com.kadiri.elkitabi.features.kazanamaz.data.local.entities.KazaNamaziEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class KazaKaydiUi(
    val id: Long,
    val toplamKaza: Int,
    val tamamlanan: Int,
    val gunlukHedef: Int
)

data class KazaNamaziUiState(
    val kazaKaydi: KazaKaydiUi? = null
)

@HiltViewModel
class KazaNamaziViewModel @Inject constructor(
    private val kazaNamaziDao: KazaNamaziDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(KazaNamaziUiState())
    val uiState: StateFlow<KazaNamaziUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            kazaNamaziDao.getAktifKaza().collect { entity ->
                _uiState.value = KazaNamaziUiState(
                    kazaKaydi = entity?.let {
                        KazaKaydiUi(it.id, it.toplamKazaSayisi, it.tamamlananSayisi, it.gunlukHedef)
                    }
                )
            }
        }
    }

    fun kazaOlustur(baslangicYili: Int, gunlukHedef: Int) {
        viewModelScope.launch {
            val gecenYil = LocalDate.now().year - baslangicYili
            val toplamKaza = gecenYil * 365 * 5
            kazaNamaziDao.insertOrUpdate(
                KazaNamaziEntity(
                    baslangicYili = baslangicYili,
                    toplamKazaSayisi = toplamKaza,
                    gunlukHedef = gunlukHedef,
                    olusturmaTarihi = LocalDate.now().toString()
                )
            )
        }
    }

    fun kazaEkle(miktar: Int) {
        val id = _uiState.value.kazaKaydi?.id ?: return
        viewModelScope.launch {
            kazaNamaziDao.addTamamlanan(id, miktar)
        }
    }
}
