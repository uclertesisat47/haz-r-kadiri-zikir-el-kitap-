package com.kadiri.elkitabi.features.silsile.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

private data class SilsileHalkasi(
    val isim: String,
    val arabicIsim: String = "",
    val vefat: String = "",
    val makam: String = ""
)

private val KADİRİ_SİLSİLESİ = listOf(
    SilsileHalkasi("Hz. Muhammed Mustafâ (s.a.v.)", "مُحَمَّد", "—", "Mekke-i Mükerreme"),
    SilsileHalkasi("Hz. Ali b. Ebî Tâlib (k.v.)", "عَلِيّ", "40 H.", "Kûfe"),
    SilsileHalkasi("Hz. Hüseyin b. Ali (r.a.)", "الحُسَيْن", "61 H.", "Kerbelâ"),
    SilsileHalkasi("Zeynü'l-Âbidîn (r.a.)", "زَيْن العَابِدِين", "94 H.", "Medine"),
    SilsileHalkasi("Muhammed el-Bâkır (r.a.)", "مُحَمَّد البَاقِر", "114 H.", "Medine"),
    SilsileHalkasi("Ca'fer es-Sâdık (r.a.)", "جَعْفَر الصَادِق", "148 H.", "Medine"),
    SilsileHalkasi("Mûsâ el-Kâzım (r.a.)", "مُوسَى الكَاظِم", "183 H.", "Bağdat"),
    SilsileHalkasi("Ma'rûf el-Kerhî (r.a.)", "مَعْرُوف الكَرْخِي", "200 H.", "Bağdat"),
    SilsileHalkasi("Seriyyü's-Sekatî (r.a.)", "سَرِيّ السَقَطِي", "253 H.", "Bağdat"),
    SilsileHalkasi("Cüneydü'l-Bağdâdî (r.a.)", "الجُنَيْد البَغْدَادِي", "298 H.", "Bağdat"),
    SilsileHalkasi("Ebû Bekr eş-Şiblî (r.a.)", "أبو بكر الشِبْلِي", "334 H.", "Bağdat"),
    SilsileHalkasi("Abdülvâhid b. Yûsuf et-Temîmî (r.a.)", "عَبْد الوَاحِد", "425 H.", "Bağdat"),
    SilsileHalkasi("Ebü'l-Ferec et-Tartûsî (r.a.)", "أبو الفَرَج", "447 H.", "Bağdat"),
    SilsileHalkasi("Ebü'l-Hasan Alî el-Hakkârî (r.a.)", "عَلِي الهَكَّارِي", "486 H.", "Hakkâri"),
    SilsileHalkasi("Sultânü'l-Evliyâ Şeyh Abdülkâdir Geylânî (k.s.)", "عَبْد القَادِر الجِيلَانِي", "561 H.", "Bağdat – Ziyaret edilir")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SilsileScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Silsile-i Şerife") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            item {
                Text(
                    text     = "Kadiri Tarikatı'nın Hz. Peygamber'e uzanan mübarek silsilesi",
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = Spacing.md)
                )
            }
            itemsIndexed(KADİRİ_SİLSİLESİ) { index, halka ->
                IslamicCard(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                    contentPadding = 10.dp
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text  = "${index + 1}",
                            style = MaterialTheme.typography.labelLarge,
                            color = GoldAccent,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(28.dp)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(halka.isim, style = MaterialTheme.typography.bodyMedium, fontWeight = if (index == 0 || index == KADİRİ_SİLSİLESİ.lastIndex) FontWeight.Bold else FontWeight.Normal, color = if (index == KADİRİ_SİLSİLESİ.lastIndex) GoldAccent else MaterialTheme.colorScheme.onSurface)
                            if (halka.arabicIsim.isNotBlank()) Text(halka.arabicIsim, style = MaterialTheme.typography.bodySmall, color = ArabesqueGold)
                            if (halka.vefat.isNotBlank() || halka.makam.isNotBlank()) {
                                Text("${halka.vefat} · ${halka.makam}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
                if (index < KADİRİ_SİLSİLESİ.lastIndex) {
                    Icon(Icons.Filled.ArrowDownward, null, tint = IslamicGreen.copy(alpha = 0.5f), modifier = Modifier.padding(start = 28.dp))
                }
            }
            item { Spacer(Modifier.height(Spacing.xxxl)) }
        }
    }
}

