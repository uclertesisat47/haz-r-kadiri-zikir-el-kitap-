package com.kadiri.elkitabi.features.planlayici.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.features.planlayici.data.local.GunlukPlanDao
import com.kadiri.elkitabi.features.planlayici.data.local.entities.GunlukPlanEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class PlanUiModel(
    val id: Long,
    val ibadetAdi: String,
    val saat: String,
    val tamamlandi: Boolean,
    val ozelMi: Boolean
)

data class ZamanBloku(
    val saat: String,
    val baslik: String,
    val oneriler: List<String>,
    val namazVakti: Boolean = false
)

data class GunlukPlanUiState(
    val planlar: List<PlanUiModel> = emptyList(),
    val namazProgrami: List<ZamanBloku> = emptyList(),
    val tamamlananSayi: Int = 0,
    val toplamSayi: Int = 0
)

@HiltViewModel
class GunlukPlanViewModel @Inject constructor(
    private val gunlukPlanDao: GunlukPlanDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(GunlukPlanUiState())
    val uiState: StateFlow<GunlukPlanUiState> = _uiState.asStateFlow()

    private val bugun = LocalDate.now().toString()

    private val sabitProgram = listOf(
        ZamanBloku("04:20", "İMSAK", listOf("Sühur vakti", "Teheccüd namazı"), true),
        ZamanBloku("05:10", "SABAH NAMAZI", listOf("Sabah namazı sünneti", "Sabah ezkarı"), true),
        ZamanBloku("07:30", "İşrak Vakti", listOf("2 rekat işrak namazı", "Kuran okuma")),
        ZamanBloku("12:15", "ÖĞLE NAMAZI", listOf("Öğle namazı sünneti", "Kaylule dinlenmesi"), true),
        ZamanBloku("15:30", "İKİNDİ NAMAZI", listOf("İkindi zikirler"), true),
        ZamanBloku("18:15", "AKŞAM NAMAZI", listOf("Akşam ezkarı", "İftar duası"), true),
        ZamanBloku("19:45", "YATSI NAMAZI", listOf("Yatsı sünneti", "Vitir namazı", "Uyku ezkarı"), true),
    )

    init {
        viewModelScope.launch {
            if (gunlukPlanDao.getTotalSayisi(bugun).first() == 0) {
                listOf(
                    GunlukPlanEntity(tarih = bugun, ibadetAdi = "Sabah Ezkarı", planlananSaat = "05:30", ozelMi = false),
                    GunlukPlanEntity(tarih = bugun, ibadetAdi = "100 Zikir", planlananSaat = "08:00", ozelMi = false),
                    GunlukPlanEntity(tarih = bugun, ibadetAdi = "Akşam Ezkarı", planlananSaat = "18:30", ozelMi = false),
                    GunlukPlanEntity(tarih = bugun, ibadetAdi = "Yatsı Virdi", planlananSaat = "20:00", ozelMi = false),
                ).forEach { gunlukPlanDao.insertPlan(it) }
            }
        }

        viewModelScope.launch {
            gunlukPlanDao.getPlanByTarih(bugun).collect { planlar ->
                val tamamlanan = planlar.count { it.tamamlandi }
                _uiState.value = GunlukPlanUiState(
                    planlar = planlar.map { PlanUiModel(it.id, it.ibadetAdi, it.planlananSaat, it.tamamlandi, it.ozelMi) },
                    namazProgrami = sabitProgram,
                    tamamlananSayi = tamamlanan,
                    toplamSayi = planlar.size
                )
            }
        }
    }

    fun planTamamla(id: Long, tamamlandi: Boolean) {
        viewModelScope.launch {
            gunlukPlanDao.setTamamlandi(id, tamamlandi)
        }
    }
}
