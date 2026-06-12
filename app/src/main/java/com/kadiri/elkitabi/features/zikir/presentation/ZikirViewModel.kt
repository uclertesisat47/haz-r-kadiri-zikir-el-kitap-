package com.kadiri.elkitabi.features.zikir.presentation

import android.os.PowerManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.core.data.preferences.UserPreferencesDataStore
import com.kadiri.elkitabi.core.utils.HapticManager
import com.kadiri.elkitabi.core.utils.ZikirAudioManager
import com.kadiri.elkitabi.features.zikir.domain.model.Zikir
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirHedef
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirSession
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirSesi
import com.kadiri.elkitabi.features.zikir.domain.usecase.GetGunlukHedefUseCase
import com.kadiri.elkitabi.features.zikir.domain.usecase.GetZikirIstatistikUseCase
import com.kadiri.elkitabi.features.zikir.domain.usecase.GetZikirListeUseCase
import com.kadiri.elkitabi.features.zikir.domain.usecase.SaveZikirSessionUseCase
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ZikirViewModel @Inject constructor(
    private val getZikirListeUseCase: GetZikirListeUseCase,
    private val saveZikirSessionUseCase: SaveZikirSessionUseCase,
    private val getZikirIstatistikUseCase: GetZikirIstatistikUseCase,
    private val getGunlukHedefUseCase: GetGunlukHedefUseCase,
    private val zikirRepository: ZikirRepository,
    private val preferences: UserPreferencesDataStore,
    private val hapticManager: HapticManager,
    private val audioManager: ZikirAudioManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    data class ZikirUiState(
        val seciliZikir: Zikir? = null,
        val mevcutSayi: Int = 0,
        val hedef: Int = 33,
        val turSayisi: Int = 0,
        val toplamSayi: Int = 0,
        val tamamlandi: Boolean = false,
        val oturumBaslangic: Long = 0L,
        val isSessionActive: Boolean = false,
        val seciliSes: ZikirSesi = ZikirSesi.TITREŞIM,
        val vibrasyonAktif: Boolean = true,
        val ekranAcikKalsin: Boolean = true,
        val zikirListesi: List<Zikir> = emptyList(),
        val isLoading: Boolean = false,
        val hata: String? = null
    )

    private val _uiState = MutableStateFlow(ZikirUiState())
    val uiState: StateFlow<ZikirUiState> = _uiState.asStateFlow()

    private var wakeLock: PowerManager.WakeLock? = null

    init {
        loadZikirler()
        loadPreferences()
    }

    private fun loadZikirler() {
        viewModelScope.launch {
            zikirRepository.seedZikirlerIfEmpty()
            getZikirListeUseCase().collect { liste ->
                _uiState.update {
                    it.copy(
                        zikirListesi = liste,
                        isLoading = false,
                        seciliZikir = it.seciliZikir ?: liste.firstOrNull()
                    )
                }
            }
        }
    }

    private fun loadPreferences() {
        viewModelScope.launch {
            preferences.zikirVibration.collect { v ->
                _uiState.update { it.copy(vibrasyonAktif = v) }
            }
        }
        viewModelScope.launch {
            preferences.ekranAcikKalsin.collect { e ->
                _uiState.update { it.copy(ekranAcikKalsin = e) }
            }
        }
        viewModelScope.launch {
            preferences.zikirSes.collect { s ->
                val ses = try { ZikirSesi.valueOf(s) } catch (e: Exception) { ZikirSesi.TITREŞIM }
                _uiState.update { it.copy(seciliSes = ses) }
                audioManager.setSes(ses.toAudio())
            }
        }
    }

    fun zikirSec(zikir: Zikir) {
        viewModelScope.launch {
            val hedef = getGunlukHedefUseCase(zikir.id)?.gunlukHedef ?: zikir.varsayilanHedef
            _uiState.update { it.copy(seciliZikir = zikir, hedef = hedef, mevcutSayi = 0, turSayisi = 0, toplamSayi = 0) }
        }
    }

    fun hedefSec(hedef: Int) {
        _uiState.update { it.copy(hedef = hedef) }
    }

    fun oturumBaslat() {
        _uiState.update { it.copy(isSessionActive = true, oturumBaslangic = System.currentTimeMillis(), mevcutSayi = 0, turSayisi = 0, toplamSayi = 0, tamamlandi = false) }
        if (_uiState.value.ekranAcikKalsin) acquireWakeLock()
    }

    fun zikirSay() {
        val state = _uiState.value
        val yeniSayi   = state.mevcutSayi + 1
        val yeniToplam = state.toplamSayi + 1

        if (_uiState.value.vibrasyonAktif) hapticManager.zikirTap()
        audioManager.playZikirTap()

        when {
            yeniSayi >= state.hedef -> {
                hapticManager.heavyImpact()
                audioManager.playTamamSesi()
                _uiState.update {
                    it.copy(
                        mevcutSayi = 0,
                        turSayisi  = it.turSayisi + 1,
                        toplamSayi = yeniToplam,
                        tamamlandi = true
                    )
                }
                viewModelScope.launch {
                    delay(2500)
                    resetTamamlandi()
                }
            }
            else -> {
                _uiState.update { it.copy(mevcutSayi = yeniSayi, toplamSayi = yeniToplam) }
            }
        }
    }

    fun sifirla() {
        _uiState.update { it.copy(mevcutSayi = 0) }
    }

    fun oturumBitir() {
        val state = _uiState.value
        val seciliZikir = state.seciliZikir ?: return
        val sure = (System.currentTimeMillis() - state.oturumBaslangic) / 1000
        viewModelScope.launch {
            saveZikirSessionUseCase(
                ZikirSession(
                    zikirId    = seciliZikir.id,
                    zikirAdi   = seciliZikir.turkishName,
                    count      = state.toplamSayi,
                    hedef      = state.hedef,
                    tarih      = state.oturumBaslangic,
                    sure       = sure,
                    tamamlandi = state.turSayisi > 0
                )
            )
        }
        _uiState.update { it.copy(isSessionActive = false, mevcutSayi = 0, toplamSayi = 0, turSayisi = 0) }
        releaseWakeLock()
    }

    fun setSes(ses: ZikirSesi) {
        viewModelScope.launch { preferences.setZikirSes(ses.name) }
        audioManager.setSes(ses.toAudio())
        _uiState.update { it.copy(seciliSes = ses) }
    }

    fun setVibrasyon(aktif: Boolean) {
        viewModelScope.launch { preferences.setZikirVibration(aktif) }
    }

    private fun resetTamamlandi() {
        _uiState.update { it.copy(tamamlandi = false) }
    }

    private fun acquireWakeLock() {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Kadiri::ZikirWakeLock")
        wakeLock?.acquire(60 * 60 * 1000L)
    }

    private fun releaseWakeLock() {
        wakeLock?.release()
        wakeLock = null
    }

    override fun onCleared() {
        super.onCleared()
        releaseWakeLock()
        audioManager.release()
    }

    private fun ZikirSesi.toAudio() = when (this) {
        ZikirSesi.TEBİH_SESİ -> ZikirAudioManager.ZikirSesi.TEBİH_SESİ
        ZikirSesi.NEY_SESİ   -> ZikirAudioManager.ZikirSesi.NEY_SESİ
        ZikirSesi.SU_SESİ    -> ZikirAudioManager.ZikirSesi.SU_SESİ
        ZikirSesi.TITREŞIM   -> ZikirAudioManager.ZikirSesi.SESSIZ
        ZikirSesi.SESSIZ     -> ZikirAudioManager.ZikirSesi.SESSIZ
    }
}
