package com.kadiri.elkitabi.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.core.utils.HijriUtils
import com.kadiri.elkitabi.core.utils.PrayerTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val hijriDate: String   = "",
    val miladi: String      = "",
    val nextPrayer: String  = "",
    val nextPrayerTime: String = "",
    val remainingTime: String  = "",
    val todayZikirCount: Int   = 0,
    val streak: Int            = 0,
    val isLoading: Boolean     = true
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            val hijriInfo = HijriUtils.todayHijri()
            _uiState.update { state ->
                state.copy(
                    hijriDate  = HijriUtils.formatHijri(hijriInfo),
                    miladi     = java.text.SimpleDateFormat("dd MMMM yyyy", java.util.Locale("tr","TR")).format(java.util.Date()),
                    isLoading  = false
                )
            }
        }
    }

    fun refresh() = loadHomeData()
}
