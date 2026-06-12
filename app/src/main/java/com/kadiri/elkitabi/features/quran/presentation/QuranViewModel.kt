package com.kadiri.elkitabi.features.quran.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class AyetUi(val numara: Int, val arapca: String, val turkce: String, val kaynak: String = "")
data class OncSure(val numara: Int, val isimArapca: String, val isimTurkce: String, val ayetSayisi: Int, val ayetler: List<AyetUi>)

data class QuranUiState(
    val secilenTab: Int = 0,
    val oncSureler: List<OncSure> = emptyList(),
    val secilenSure: OncSure? = null,
    val gunAyeti: AyetUi? = null,
    val cuzDurumu: List<Boolean> = List(30) { false }
)

@HiltViewModel
class QuranViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuranUiState())
    val uiState: StateFlow<QuranUiState> = _uiState.asStateFlow()

    private val onceSureler = listOf(
        OncSure(
            numara = 1, isimArapca = "الفاتحة", isimTurkce = "Fatiha Suresi", ayetSayisi = 7,
            ayetler = listOf(
                AyetUi(1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "Rahman ve Rahim olan Allah'ın adıyla"),
                AyetUi(2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "Hamd, âlemlerin Rabbi Allah'a mahsustur"),
                AyetUi(3, "الرَّحْمَٰنِ الرَّحِيمِ", "O, Rahman'dır, Rahim'dir"),
                AyetUi(4, "مَالِكِ يَوْمِ الدِّينِ", "Din (hesap) gününün sahibidir"),
                AyetUi(5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "Yalnız Sana ibadet eder, yalnız Senden yardım dileriz"),
                AyetUi(6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "Bizi doğru yola ilet"),
                AyetUi(7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "Nimet verdiklerinin yoluna; gazaba uğrayanların ve sapkınların yoluna değil")
            )
        ),
        OncSure(
            numara = 112, isimArapca = "الإخلاص", isimTurkce = "İhlas Suresi", ayetSayisi = 4,
            ayetler = listOf(
                AyetUi(1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "De ki: O Allah birdir"),
                AyetUi(2, "اللَّهُ الصَّمَدُ", "Allah Samed'dir (Her şey O'na muhtaçtır)"),
                AyetUi(3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "Doğurmamış ve doğurulmamıştır"),
                AyetUi(4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "Hiçbir şey O'nun dengi değildir")
            )
        ),
        OncSure(
            numara = 113, isimArapca = "الفلق", isimTurkce = "Felak Suresi", ayetSayisi = 5,
            ayetler = listOf(
                AyetUi(1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "De ki: Sabahın Rabbine sığınırım"),
                AyetUi(2, "مِن شَرِّ مَا خَلَقَ", "Yarattığı şeylerin şerrinden"),
                AyetUi(3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "Bastırınca gecenin şerrinden"),
                AyetUi(4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "Düğümlere üfleyenlerin şerrinden"),
                AyetUi(5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "Haset ettiğinde hasetçinin şerrinden")
            )
        ),
        OncSure(
            numara = 114, isimArapca = "الناس", isimTurkce = "Nas Suresi", ayetSayisi = 6,
            ayetler = listOf(
                AyetUi(1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "De ki: İnsanların Rabbine sığınırım"),
                AyetUi(2, "مَلِكِ النَّاسِ", "İnsanların Melikine"),
                AyetUi(3, "إِلَٰهِ النَّاسِ", "İnsanların İlahına"),
                AyetUi(4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "Sinsi vesvesecinin şerrinden"),
                AyetUi(5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "İnsanların göğüslerine vesvese sokan"),
                AyetUi(6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "Cinlerden ve insanlardan olan")
            )
        ),
        OncSure(
            numara = 2, isimArapca = "آية الكرسي", isimTurkce = "Ayetel Kürsi", ayetSayisi = 1,
            ayetler = listOf(
                AyetUi(255, "اللَّهُ لَا إِلَهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ", "Allah, kendisinden başka hiçbir ilah olmayandır. Diridir, kayyumdur. O'nu ne bir uyuklama ne de uyku yakalar. Göklerde ve yerde ne varsa O'nundur.", "Bakara 255")
            )
        )
    )

    private val gunAyetleri = listOf(
        AyetUi(1, "وَمَن يَتَّقِ اللَّهَ يَجْعَل لَّهُ مَخْرَجًا", "Kim Allah'a karşı gelmekten sakınırsa, Allah ona bir çıkış yolu açar.", "Talak 2"),
        AyetUi(2, "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا", "Şüphesiz, zorlukla birlikte bir kolaylık vardır.", "İnşirah 5"),
        AyetUi(3, "وَاللَّهُ خَيْرُ الرَّازِقِينَ", "Allah, rızık verenlerin en hayırlısıdır.", "Cuma 11"),
        AyetUi(4, "إِنَّ اللَّهَ مَعَ الصَّابِرِينَ", "Şüphesiz Allah, sabredenlerle beraberdir.", "Bakara 153"),
        AyetUi(5, "وَهُوَ عَلَى كُلِّ شَيْءٍ قَدِيرٌ", "O, her şeye kadirdir.", "Bakara 20")
    )

    init {
        viewModelScope.launch {
            val gunIndex = LocalDate.now().dayOfYear % gunAyetleri.size
            _uiState.value = QuranUiState(
                oncSureler = onceSureler,
                gunAyeti = gunAyetleri[gunIndex]
            )
        }
    }

    fun tabDegistir(tab: Int) {
        _uiState.value = _uiState.value.copy(secilenTab = tab)
    }

    fun sureSec(sure: OncSure) {
        _uiState.value = _uiState.value.copy(secilenSure = sure)
    }

    fun cuzIsaretle(idx: Int, tamamlandi: Boolean) {
        val yeni = _uiState.value.cuzDurumu.toMutableList()
        if (idx < yeni.size) yeni[idx] = tamamlandi
        _uiState.value = _uiState.value.copy(cuzDurumu = yeni)
    }
}
