package com.kadiri.elkitabi.features.dua.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.ArabicTextMedium
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

private data class Dua(val baslik: String, val arabicText: String, val turkce: String, val kaynak: String)

private val DUALAR = listOf(
    Dua("Sabah Duası", "اللَّهُمَّ بِكَ أَصْبَحْنَا وَبِكَ أَمْسَيْنَا", "Allah'ım! Senin adınla sabahladık ve senin adınla akşamladık.", "Ebu Davud"),
    Dua("Akşam Duası", "اللَّهُمَّ بِكَ أَمْسَيْنَا وَبِكَ أَصْبَحْنَا", "Allah'ım! Senin adınla akşamladık ve senin adınla sabahladık.", "Ebu Davud"),
    Dua("Yemek Duası", "بِسْمِ اللهِ وَعَلَى بَرَكَةِ اللهِ", "Allah'ın adıyla ve Allah'ın bereketlyle (başlıyorum).", "İbn Mace"),
    Dua("Yolculuk Duası", "اللَّهُمَّ إِنَّا نَسْأَلُكَ فِي سَفَرِنَا هَذَا الْبِرَّ وَالتَّقْوَى", "Allahım! Biz bu yolculuğumuzda Senden iyilik ve takva istiyoruz.", "Müslim"),
    Dua("Uyumadan Önce", "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا", "Allah'ım! Senin adınla ölür (uyur) ve dirilirim.", "Buhari"),
    Dua("Uyanınca", "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ", "Bizi öldürdükten sonra dirilten Allah'a hamdolsun. Dönüş O'nadır.", "Buhari"),
    Dua("Kaside Başlangıcı", "اللَّهُمَّ صَلِّ وَسَلِّمْ وَبَارِكْ عَلَى سَيِّدِنَا مُحَمَّدٍ", "Allahım! Efendimiz Muhammed'e salat, selam ve bereket ihsan eyle.", "Genel"),
    Dua("Hatm-i Tehlil", "لَا إِلَهَ إِلَّا اللهُ مُحَمَّدٌ رَسُولُ اللهِ", "Allah'tan başka ilah yoktur. Muhammed Allah'ın resulüdür.", "Genel"),
    Dua("Kadiri Salavat", "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ النَّبِيِّ الْأُمِّيِّ", "Allahım! Ümmî Nebi Muhammed'e salat eyle.", "Genel"),
    Dua("Seyyidü'l-İstiğfar", "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ", "Allahım! Sen benim Rabbimsin, Senden başka ilah yoktur.", "Buhari"),
    Dua("Fatiha",     "بِسْمِ اللهِ الرَّحْمَنِ الرَّحِيمِ — الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "Rahman ve Rahim olan Allah'ın adıyla — Hamd âlemlerin Rabbi Allah'a mahsustur.", "Kuran 1:1-7"),
    Dua("Yasin Başlangıcı", "يس — وَالْقُرْآنِ الْحَكِيمِ", "Yâsîn — Hikmet dolu Kur'an'a andolsun.", "Kuran 36:1-2")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DualarScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dualar ve Zikirler") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            items(DUALAR) { dua ->
                DuaKarti(dua = dua, modifier = Modifier.padding(bottom = Spacing.sm))
            }
            item { Spacer(Modifier.height(Spacing.xxxl)) }
        }
    }
}

@Composable
private fun DuaKarti(dua: Dua, modifier: Modifier = Modifier) {
    IslamicCard(modifier = modifier.fillMaxWidth()) {
        Column {
            Text(dua.baslik,     style = MaterialTheme.typography.titleSmall, color = GoldAccent)
            Spacer(Modifier.height(4.dp))
            Text(dua.arabicText, style = ArabicTextMedium.copy(fontSize = 18.sp), color = ArabesqueGold)
            Spacer(Modifier.height(4.dp))
            Text(dua.turkce,     style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
            Text("— ${dua.kaynak}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private val Int.dp  get() = androidx.compose.ui.unit.Dp(this.toFloat())
private val Int.sp  get() = androidx.compose.ui.unit.TextUnit(this.toFloat(), androidx.compose.ui.unit.TextUnitType.Sp)
