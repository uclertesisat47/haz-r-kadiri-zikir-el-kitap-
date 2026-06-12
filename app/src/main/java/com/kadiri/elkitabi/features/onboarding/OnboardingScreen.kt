package com.kadiri.elkitabi.features.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadiri.elkitabi.core.designsystem.theme.*

@Composable
fun OnboardingScreen(onTamamla: () -> Unit) {
    var sayfa by remember { mutableIntStateOf(0) }
    var muntesin by remember { mutableStateOf(true) }
    var gunlukZikir by remember { mutableStateOf("100") }
    var sehir by remember { mutableStateOf("İstanbul") }

    val sayfalar = listOf(
        OnboardingSayfa(
            emoji = "🕌",
            baslik = "Bismillah",
            aciklama = "Kadiri Tarikatı El Kitabı'na hoş geldiniz. Bu uygulama manevi yolculuğunuzda size rehberlik edecektir."
        ),
        OnboardingSayfa(
            emoji = "🌿",
            baslik = "Sizi Tanıyalım",
            aciklama = "Size daha iyi hizmet verebilmemiz için birkaç soru soralım."
        ),
        OnboardingSayfa(
            emoji = "🎯",
            baslik = "Hedefleriniz",
            aciklama = "Günlük zikir hedefinizi belirleyin."
        ),
        OnboardingSayfa(
            emoji = "📍",
            baslik = "Konum",
            aciklama = "Namaz vakitleri için şehrinizi seçin."
        ),
        OnboardingSayfa(
            emoji = "✨",
            baslik = "Hazır!",
            aciklama = "Her şey ayarlandı. Manevi yolculuğunuz başlıyor!"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BackgroundDark, PrimaryContainerDark)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier.height(32.dp))

            AnimatedContent(
                targetState = sayfa,
                transitionSpec = {
                    slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                },
                label = "sayfa"
            ) { sayfaIdx ->
                val s = sayfalar[sayfaIdx]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(s.emoji, fontSize = 80.sp)
                    Text(s.baslik, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = GoldAccent, textAlign = TextAlign.Center)
                    Text(s.aciklama, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center, color = OnBackgroundDark.copy(alpha = 0.8f))

                    when (sayfaIdx) {
                        1 -> {
                            Spacer(Modifier.height(16.dp))
                            Text("Kadiri müntesibi misiniz?", style = MaterialTheme.typography.bodyLarge, color = GoldAccent)
                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                listOf("Evet" to true, "Hayır" to false, "Araştırıyorum" to null).forEach { (label, value) ->
                                    FilterChip(
                                        selected = if (value == null) !muntesin else muntesin == value,
                                        onClick = { if (value != null) muntesin = value },
                                        label = { Text(label) }
                                    )
                                }
                            }
                        }
                        2 -> {
                            Spacer(Modifier.height(16.dp))
                            Text("Günlük Zikir Hedefi:", style = MaterialTheme.typography.bodyLarge, color = GoldAccent)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("33", "99", "100", "500", "1000").forEach { hedef ->
                                    FilterChip(
                                        selected = gunlukZikir == hedef,
                                        onClick = { gunlukZikir = hedef },
                                        label = { Text(hedef) }
                                    )
                                }
                            }
                        }
                        3 -> {
                            Spacer(Modifier.height(16.dp))
                            OutlinedTextField(
                                value = sehir,
                                onValueChange = { sehir = it },
                                label = { Text("Şehriniz") },
                                singleLine = true
                            )
                        }
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    sayfalar.indices.forEach { idx ->
                        Box(
                            modifier = Modifier
                                .size(if (idx == sayfa) 12.dp else 8.dp)
                                .background(
                                    if (idx == sayfa) GoldAccent else GoldAccent.copy(alpha = 0.3f),
                                    CircleShape
                                )
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    if (sayfa > 0) {
                        OutlinedButton(onClick = { sayfa-- }) { Text("Geri") }
                    }
                    Button(
                        onClick = {
                            if (sayfa < sayfalar.size - 1) sayfa++
                            else onTamamla()
                        }
                    ) {
                        Text(if (sayfa < sayfalar.size - 1) "Devam →" else "Başla 🌿")
                    }
                }
                if (sayfa < sayfalar.size - 1) {
                    TextButton(onClick = onTamamla) {
                        Text("Atla", color = GoldAccent.copy(alpha = 0.6f))
                    }
                }
            }
        }
    }
}

data class OnboardingSayfa(val emoji: String, val baslik: String, val aciklama: String)
