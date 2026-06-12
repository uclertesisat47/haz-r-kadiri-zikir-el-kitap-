package com.kadiri.elkitabi.features.mooddua.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject

@Serializable
data class DuaItem(
    val arabicText: String,
    val turkishMeaning: String,
    val kaynak: String,
    val sesResId: String = ""
)

@Serializable
data class AyetItem(
    val sure: String,
    val ayet: String,
    val arabicText: String,
    val turkishMeaning: String
)

@Serializable
data class MoodItem(
    val id: String,
    val emoji: String,
    val isim: String,
    val renk: String,
    val dualar: List<DuaItem> = emptyList(),
    val ayetler: List<AyetItem> = emptyList()
)

@Serializable
private data class DuygularJson(val ruhHali: List<MoodItem>)

data class MoodDuaUiState(
    val moodlar: List<MoodItem> = emptyList(),
    val secilenMood: MoodItem? = null
)

@HiltViewModel
class MoodDuaViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodDuaUiState())
    val uiState: StateFlow<MoodDuaUiState> = _uiState.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true }

    init {
        viewModelScope.launch {
            try {
                val jsonString = context.assets.open("data/duygular.json")
                    .bufferedReader().readText()
                val data = json.decodeFromString<DuygularJson>(jsonString)
                _uiState.value = MoodDuaUiState(moodlar = data.ruhHali)
            } catch (_: Exception) {}
        }
    }

    fun moodSec(mood: MoodItem) {
        _uiState.value = _uiState.value.copy(secilenMood = mood)
    }

    fun moodTemizle() {
        _uiState.value = _uiState.value.copy(secilenMood = null)
    }
}
