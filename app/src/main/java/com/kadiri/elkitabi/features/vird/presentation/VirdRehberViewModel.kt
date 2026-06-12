package com.kadiri.elkitabi.features.vird.presentation

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
data class VirdAdimJson(
    val id: Int, val baslik: String, val arabicText: String,
    val turkishMeaning: String, val sayi: Int
)

@Serializable
private data class VirdBolum(val baslik: String, val tahminiSure: String, val adimlar: List<VirdAdimJson>)

@Serializable
private data class VirdJson(
    val sabahVirdi: VirdBolum,
    val aksamVirdi: VirdBolum,
    val hatmiHacegan: VirdBolum
)

data class VirdAdimUi(
    val id: Int, val baslik: String, val arabicText: String,
    val turkishMeaning: String, val sayi: Int
)

data class VirdUiState(
    val sabahAdimlar: List<VirdAdimUi> = emptyList(),
    val aksamAdimlar: List<VirdAdimUi> = emptyList(),
    val hatimAdimlar: List<VirdAdimUi> = emptyList(),
    val aktifAdim: Int = 0
)

@HiltViewModel
class VirdRehberViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(VirdUiState())
    val uiState: StateFlow<VirdUiState> = _uiState.asStateFlow()
    private val json = Json { ignoreUnknownKeys = true }

    init {
        viewModelScope.launch {
            try {
                val raw = context.assets.open("data/vird.json").bufferedReader().readText()
                val data = json.decodeFromString<VirdJson>(raw)
                _uiState.value = VirdUiState(
                    sabahAdimlar = data.sabahVirdi.adimlar.map { VirdAdimUi(it.id, it.baslik, it.arabicText, it.turkishMeaning, it.sayi) },
                    aksamAdimlar = data.aksamVirdi.adimlar.map { VirdAdimUi(it.id, it.baslik, it.arabicText, it.turkishMeaning, it.sayi) },
                    hatimAdimlar = data.hatmiHacegan.adimlar.map { VirdAdimUi(it.id, it.baslik, it.arabicText, it.turkishMeaning, it.sayi) }
                )
            } catch (_: Exception) {}
        }
    }

    fun adimIlerle() {
        val state = _uiState.value
        val maks = maxOf(state.sabahAdimlar.size, state.aksamAdimlar.size) - 1
        if (state.aktifAdim < maks) {
            _uiState.value = state.copy(aktifAdim = state.aktifAdim + 1)
        }
    }
}
