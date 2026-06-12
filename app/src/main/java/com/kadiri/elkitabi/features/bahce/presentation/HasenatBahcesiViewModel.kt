package com.kadiri.elkitabi.features.bahce.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.core.designsystem.theme.*
import com.kadiri.elkitabi.features.bahce.data.local.HasenatDao
import com.kadiri.elkitabi.features.bahce.data.local.entities.HasenatBitkiEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

enum class BitkiTur(val isim: String, val renk: Color) {
    NAMAZ("5 Vakit Namaz", IslamicGreen),
    ZIKIR("Zikirmatik", GoldAccent),
    KURAN("Kuran Okuma", MysticPurple),
    SABAH_EZKAR("Sabah Ezkarı", WarmAmber),
    AKSAM_EZKAR("Akşam Ezkarı", PrimaryDark),
    SUNNNET("Sünnet Namazlar", Color(0xFF40A0FF)),
    TAHECCUD("Teheccüd", TertiaryDark),
    SADAKA("Sadaka / Hayır", Color(0xFFFF6B35)),
    ORUC("Oruç", Color(0xFF8BC34A)),
    KADIRI_VIRD("Kadiri Virdi", ArabesqueGold)
}

data class BitkiUiModel(
    val id: Long,
    val tur: String,
    val saglik: Int,
    val seviye: Int
)

data class HasenatUiState(
    val bitkiler: List<BitkiUiModel> = emptyList(),
    val totalHasenat: Int = 0,
    val saglikliSayi: Int = 0,
    val streak: Int = 0
)

@HiltViewModel
class HasenatBahcesiViewModel @Inject constructor(
    private val hasenatDao: HasenatDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HasenatUiState())
    val uiState: StateFlow<HasenatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                hasenatDao.getAllBitkiler(),
                hasenatDao.getTotalHasenat(),
                hasenatDao.getSaglikliSayi()
            ) { bitkiler, total, saglikli ->
                HasenatUiState(
                    bitkiler = bitkiler.map { BitkiUiModel(it.id, it.tur, it.saglik, it.seviye) },
                    totalHasenat = total ?: 0,
                    saglikliSayi = saglikli,
                    streak = hesaplaStreak(bitkiler)
                )
            }.collect { _uiState.value = it }
        }
    }

    fun bitkiEkle(tur: BitkiTur) {
        viewModelScope.launch {
            val bugun = LocalDate.now().toString()
            hasenatDao.insertBitki(
                HasenatBitkiEntity(
                    tur = tur.isim,
                    edinimTarihi = bugun,
                    sulamaTarihi = bugun,
                    saglik = 100,
                    seviye = 1,
                    pozisyonX = (10..90).random().toFloat(),
                    pozisyonY = (10..90).random().toFloat()
                )
            )
        }
    }

    fun bitkiSula(id: Long) {
        viewModelScope.launch {
            val bugun = LocalDate.now().toString()
            hasenatDao.updateSaglik(id, 100, bugun)
        }
    }

    private fun hesaplaStreak(bitkiler: List<HasenatBitkiEntity>): Int {
        if (bitkiler.isEmpty()) return 0
        val bugun = LocalDate.now()
        return bitkiler.filter {
            val sulama = LocalDate.parse(it.sulamaTarihi)
            !bugun.minusDays(1).isAfter(sulama)
        }.size
    }
}
