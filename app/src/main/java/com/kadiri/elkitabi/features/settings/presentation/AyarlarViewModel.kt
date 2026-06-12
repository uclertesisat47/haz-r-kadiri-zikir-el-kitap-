package com.kadiri.elkitabi.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kadiri.elkitabi.core.data.preferences.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AyarlarUiState(
    val isDarkMode: Boolean           = true,
    val notificationsEnabled: Boolean = true,
    val language: String              = "tr",
    val fontSize: Float               = 16f
)

@HiltViewModel
class AyarlarViewModel @Inject constructor(
    private val prefs: UserPreferencesDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(AyarlarUiState())
    val uiState: StateFlow<AyarlarUiState> = _uiState.asStateFlow()

    init {
        combine(
            prefs.darkTheme,
            prefs.bildirimNamaz,
            prefs.language,
            prefs.fontSizeScale
        ) { dark, notif, lang, font ->
            AyarlarUiState(
                isDarkMode           = dark,
                notificationsEnabled = notif,
                language             = lang,
                fontSize             = font * 16f
            )
        }.onEach { state ->
            _uiState.update { state }
        }.launchIn(viewModelScope)
    }

    fun setDarkMode(enabled: Boolean) =
        viewModelScope.launch { prefs.setDarkTheme(enabled) }

    fun setNotifications(enabled: Boolean) =
        viewModelScope.launch { prefs.setBildirimNamaz(enabled) }

    fun setLanguage(lang: String) =
        viewModelScope.launch { prefs.setLanguage(lang) }

    fun setFontSize(size: Float) =
        viewModelScope.launch { prefs.setFontSizeScale(size / 16f) }
}
