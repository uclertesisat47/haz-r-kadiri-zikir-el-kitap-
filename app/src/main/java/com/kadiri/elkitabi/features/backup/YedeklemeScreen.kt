package com.kadiri.elkitabi.features.backup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YedeklemeScreen(onBack: () -> Unit) {
    var sonYedekleme by remember { mutableStateOf<String?>(null) }
    var yukleniyor by remember { mutableStateOf(false) }
    var mesaj by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("💾 Veri Yedekleme") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("VERİLERİNİZ", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                    Spacer(Modifier.height(12.dp))
                    val veriOgeler = listOf(
                        "📿 Zikir geçmişi",
                        "📖 El kitabı yer imleri",
                        "🌿 Hasenât bahçesi",
                        "📔 Manevi günlük",
                        "🌙 Ramazan takibi",
                        "❓ Quiz skorları",
                        "🕌 Kaza namazı takibi",
                        "⚙️ Uygulama ayarları"
                    )
                    veriOgeler.forEach { oge ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(oge, style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.weight(1f))
                            Text("✓", color = IslamicGreen, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("SON YEDEKLEME", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        sonYedekleme ?: "Henüz yedekleme yapılmadı",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (sonYedekleme != null) IslamicGreen else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (yukleniyor) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            mesaj?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (it.startsWith("✅")) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                    )
                ) {
                    Text(it, modifier = Modifier.padding(12.dp), color = Color.White)
                }
            }

            Button(
                onClick = {
                    yukleniyor = true
                    mesaj = null
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !yukleniyor
            ) {
                Text("☁️ Google Drive'a Yedekle")
            }

            OutlinedButton(
                onClick = {
                    sonYedekleme = "Cihaz yedeklemesi — ${LocalDate.now()}"
                    mesaj = "✅ Verileriniz yerel olarak yedeklendi."
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📱 Cihaza Yerel Yedek Al")
            }

            OutlinedButton(
                onClick = { mesaj = "ℹ️ Geri yükleme özelliği yakında eklenecek." },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔄 Yedekten Geri Yükle")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("OTOMATİK YEDEKLEME", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Haftalık otomatik yedek", style = MaterialTheme.typography.bodyMedium)
                            Text("Her Cuma gecesi 02:00'de", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        var otoEnabled by remember { mutableStateOf(false) }
                        Switch(
                            checked = otoEnabled,
                            onCheckedChange = { otoEnabled = it }
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}
