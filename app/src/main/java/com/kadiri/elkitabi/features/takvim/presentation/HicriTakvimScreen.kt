package com.kadiri.elkitabi.features.takvim.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.core.utils.HijriUtils
import androidx.compose.ui.unit.dp

private val HİCRİ_AYLAR = listOf(
    "Muharrem", "Safer", "Rebîü'l-Evvel", "Rebîü'l-Âhir",
    "Cemâziyelevvel", "Cemâziyelâhir", "Receb", "Şaban",
    "Ramazan", "Şevval", "Zilkade", "Zilhicce"
)

private val MUKADDESGÜNLERİ = mapOf(
    1 to listOf(Pair(1, "Hicri Yılbaşı"), Pair(10, "Âşûrâ Günü")),
    3 to listOf(Pair(12, "Mevlid-i Nebî (Rebîü'l-Evvel)")),
    7 to listOf(Pair(27, "Regâib Gecesi"), Pair(27, "Mîraç Kandili (tahminen)")),
    8 to listOf(Pair(15, "Berât Gecesi")),
    9 to listOf(Pair(1, "Ramazan Başlangıcı"), Pair(27, "Kadir Gecesi (tahminen)")),
    10 to listOf(Pair(1, "Ramazan Bayramı")),
    12 to listOf(Pair(10, "Kurban Bayramı"))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HicriTakvimScreen(onBack: () -> Unit) {
    val bugun       = remember { HijriUtils.todayHijri() }
    var ayOffset    by remember { mutableIntStateOf(0) }
    val gosterilen  = remember(ayOffset) { HijriUtils.addMonths(bugun, ayOffset) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hicrî Takvim") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.md)
        ) {
            Spacer(Modifier.height(Spacing.md))

            // Navigasyon bar
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                IconButton(onClick = { ayOffset-- }) { Icon(Icons.Filled.ChevronLeft, "Önceki") }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text  = HİCRİ_AYLAR.getOrElse(gosterilen.month - 1) { "Ay" },
                        style = MaterialTheme.typography.titleLarge,
                        color = GoldAccent
                    )
                    Text(
                        text  = "${gosterilen.year} H.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { ayOffset++ }) { Icon(Icons.Filled.ChevronRight, "Sonraki") }
            }

            Spacer(Modifier.height(Spacing.md))

            // Bugün kartı
            IslamicCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Bugün", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(HijriUtils.formatHijri(bugun), style = MaterialTheme.typography.titleMedium, color = GoldAccent)
                    }
                    Text("☾", style = MaterialTheme.typography.headlineLarge, color = ArabesqueGold)
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // Takvim grid (1-30 gün)
            Text("Ay Takvimi", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(Spacing.sm))

            val gunSayisi = 30
            val gunList = (1..gunSayisi).toList()
            val muqaddesGunler = MUKADDESGÜNLERİ[gosterilen.month] ?: emptyList()

            gunList.chunked(7).forEach { hafta ->
                Row(
                    modifier              = Modifier.fillMaxWidth().padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    hafta.forEach { gun ->
                        val bugunMu    = gosterilen.month == bugun.month && gosterilen.year == bugun.year && gun == bugun.day
                        val mukaddesMi = muqaddesGunler.any { it.first == gun }
                        IslamicCard(
                            modifier       = Modifier.weight(1f),
                            contentPadding = 6.dp
                        ) {
                            Text(
                                text      = "$gun",
                                style     = MaterialTheme.typography.bodySmall,
                                color     = when { bugunMu -> GoldAccent; mukaddesMi -> IslamicGreen; else -> MaterialTheme.colorScheme.onSurface },
                                textAlign = TextAlign.Center,
                                modifier  = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    // Boş slot
                    repeat(7 - hafta.size) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // Mübarek günler
            if (muqaddesGunler.isNotEmpty()) {
                Text("Bu Aydaki Mübarek Günler", style = MaterialTheme.typography.titleSmall, color = IslamicGreen)
                Spacer(Modifier.height(Spacing.sm))
                muqaddesGunler.forEach { (gun, isim) ->
                    Row(modifier = Modifier.padding(bottom = 4.dp)) {
                        Text("${HİCRİ_AYLAR.getOrElse(gosterilen.month - 1) { "" }} $gun: ", color = GoldAccent, style = MaterialTheme.typography.bodySmall)
                        Text(isim, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(Modifier.height(Spacing.xxxl))
        }
    }
}

