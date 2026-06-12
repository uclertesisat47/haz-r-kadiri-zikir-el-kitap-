package com.kadiri.elkitabi.features.ramazan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.features.ramazan.data.local.RamazanDao
import com.kadiri.elkitabi.features.ramazan.data.local.entities.RamazanGunEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class RamazanGunUi(val gun: Int, val orucTutuldu: Boolean)

data class RamazanUiState(
    val bugunGun: Int = 15,
    val hicriYil: String = "1447",
    val imsakVakti: String = "04:23",
    val iftarVakti: String = "20:47",
    val kalonSure: String = "00:00:00",
    val iftaraKaliyor: Boolean = true,
    val bugunOrucu: Boolean = false,
    val bugunTeravih: Boolean = false,
    val bugunHatim: Int = 0,
    val gunler: List<RamazanGunUi> = emptyList(),
    val tutulanOruc: Int = 0,
    val kilinanTeravih: Int = 0,
    val toplamHatimSayfa: Int = 0
)

@HiltViewModel
class RamazanViewModel @Inject constructor(
    private val ramazanDao: RamazanDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(RamazanUiState())
    val uiState: StateFlow<RamazanUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                ramazanDao.getAllGunler(),
                ramazanDao.getTutulanOrucSayisi(),
                ramazanDao.getKilinanTeravihSayisi(),
                ramazanDao.getTotalHatimSayfa()
            ) { gunler, orucs, teravihler, hatim ->
                val bugun = LocalDate.now().toString()
                val bugunData = gunler.find { it.tarih == bugun }
                val iftarSaat = 20
                val iftarDak = 47
                val simdiki = LocalTime.now()
                val kalan = if (simdiki.hour < iftarSaat || (simdiki.hour == iftarSaat && simdiki.minute < iftarDak)) {
                    val kalanDak = (iftarSaat * 60 + iftarDak) - (simdiki.hour * 60 + simdiki.minute)
                    "%02d:%02d:00".format(kalanDak / 60, kalanDak % 60)
                } else "İftar Vakti!"
                RamazanUiState(
                    bugunGun = 15,
                    kalonSure = kalan,
                    iftaraKaliyor = simdiki.hour < iftarSaat,
                    bugunOrucu = bugunData?.orucTutuldu ?: false,
                    bugunTeravih = bugunData?.teravihKilindi ?: false,
                    bugunHatim = bugunData?.hatimSayfa ?: 0,
                    gunler = gunler.mapIndexed { i, g -> RamazanGunUi(i + 1, g.orucTutuldu) },
                    tutulanOruc = orucs,
                    kilinanTeravih = teravihler,
                    toplamHatimSayfa = hatim ?: 0
                )
            }.collect { _uiState.value = it }
        }
    }

    fun orucGuncelle(deger: Boolean) {
        viewModelScope.launch {
            val bugun = LocalDate.now().toString()
            val mevcut = ramazanDao.getGunByTarih(bugun)
            ramazanDao.insertOrUpdate((mevcut ?: RamazanGunEntity(tarih = bugun)).copy(orucTutuldu = deger))
        }
    }

    fun teravihGuncelle(deger: Boolean) {
        viewModelScope.launch {
            val bugun = LocalDate.now().toString()
            val mevcut = ramazanDao.getGunByTarih(bugun)
            ramazanDao.insertOrUpdate((mevcut ?: RamazanGunEntity(tarih = bugun)).copy(teravihKilindi = deger))
        }
    }

    fun hatimGuncelle(sayfa: Int) {
        viewModelScope.launch {
            val bugun = LocalDate.now().toString()
            val mevcut = ramazanDao.getGunByTarih(bugun)
            ramazanDao.insertOrUpdate((mevcut ?: RamazanGunEntity(tarih = bugun)).copy(hatimSayfa = sayfa))
        }
    }
}
