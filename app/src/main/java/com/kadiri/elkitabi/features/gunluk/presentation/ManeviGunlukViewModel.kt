package com.kadiri.elkitabi.features.gunluk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.features.gunluk.data.local.GunlukDao
import com.kadiri.elkitabi.features.gunluk.data.local.entities.GunlukEntryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class GunlukEntryUi(
    val tarih: String,
    val ruhHaliEmoji: String,
    val puan: Int,
    val sukur: String,
    val tefekkur: String
)

data class ManeviGunlukUiState(
    val sonGunler: List<GunlukEntryUi> = emptyList(),
    val toplamGun: Int = 0,
    val ortalamaRuhHali: Double = 0.0,
    val haftalikZikir: Int = 0,
    val yillikVeri: List<Boolean> = List(365) { false }
)

@HiltViewModel
class ManeviGunlukViewModel @Inject constructor(
    private val gunlukDao: GunlukDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManeviGunlukUiState())
    val uiState: StateFlow<ManeviGunlukUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            gunlukDao.getAllEntries().collect { entries ->
                val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                val ort = if (entries.isEmpty()) 0.0
                    else entries.map { it.ruhHaliPuani }.average()
                val haftalikZikir = entries.take(7).sumOf { it.gunZikirSayisi }
                val yillikVeri = (1..365).map { gun ->
                    val tarih = LocalDate.now().minusDays((365 - gun).toLong()).toString()
                    entries.any { it.tarih == tarih }
                }
                _uiState.value = ManeviGunlukUiState(
                    sonGunler = entries.take(20).map {
                        val tarihParsed = try {
                            LocalDate.parse(it.tarih).format(format)
                        } catch (_: Exception) { it.tarih }
                        GunlukEntryUi(
                            tarih = tarihParsed,
                            ruhHaliEmoji = it.ruhHali,
                            puan = it.ruhHaliPuani,
                            sukur = it.sukurNotu,
                            tefekkur = it.tefekkurNotu
                        )
                    },
                    toplamGun = entries.size,
                    ortalamaRuhHali = ort,
                    haftalikZikir = haftalikZikir,
                    yillikVeri = yillikVeri
                )
            }
        }
    }

    fun yeniGirisEkle(sukur: String, tefekkur: String, puan: Int) {
        viewModelScope.launch {
            gunlukDao.insertEntry(
                GunlukEntryEntity(
                    tarih = LocalDate.now().toString(),
                    ruhHali = "😊",
                    ruhHaliPuani = puan,
                    sukurNotu = sukur,
                    tefekkurNotu = tefekkur
                )
            )
        }
    }
}
