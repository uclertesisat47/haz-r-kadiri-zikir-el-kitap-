package com.kadiri.elkitabi.features.amel.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.core.utils.DateUtils
import com.kadiri.elkitabi.features.amel.data.local.AmelDao
import com.kadiri.elkitabi.features.amel.data.local.entities.AmelEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AmelDefteriUiState(
    val bugunAmeller: List<AmelEntity> = emptyList(),
    val gecmisAmeller: List<AmelEntity> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class AmelDefteriViewModel @Inject constructor(
    private val amelDao: AmelDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(AmelDefteriUiState())
    val uiState: StateFlow<AmelDefteriUiState> = _uiState.asStateFlow()

    init {
        loadAmeller()
    }

    private fun loadAmeller() {
        val today = DateUtils.startOfDay(System.currentTimeMillis())
        amelDao.getBugunAmeller(today).onEach { liste ->
            _uiState.update { it.copy(bugunAmeller = liste, isLoading = false) }
        }.launchIn(viewModelScope)
        amelDao.getAllAmeller().onEach { liste ->
            _uiState.update { it.copy(gecmisAmeller = liste) }
        }.launchIn(viewModelScope)
    }

    fun amelEkle(baslik: String, amelTuru: String, miktar: Int = 1, birim: String = "kez") {
        viewModelScope.launch {
            amelDao.insertAmel(AmelEntity(
                baslik   = baslik,
                amelTuru = amelTuru,
                miktar   = miktar,
                birim    = birim
            ))
        }
    }

    fun amelTamamla(amelId: Long) {
        viewModelScope.launch {
            val amel = _uiState.value.bugunAmeller.find { it.id == amelId } ?: return@launch
            amelDao.updateAmel(amel.copy(tamamlandi = true))
        }
    }

    fun amelSil(amelId: Long) {
        viewModelScope.launch { amelDao.deleteAmel(amelId) }
    }
}
