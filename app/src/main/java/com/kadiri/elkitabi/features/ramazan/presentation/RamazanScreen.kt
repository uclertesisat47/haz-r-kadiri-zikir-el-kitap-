package com.kadiri.elkitabi.features.ramazan.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RamazanScreen(
    onBack: () -> Unit,
    viewModel: RamazanViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🌙 Ramazan Modu", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            // Başlık
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HilalCizimi(gun = state.bugunGun, modifier = Modifier.size(80.dp))
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Ramazan-ı Şerif — ${state.hicriYil}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                    Text(
                        text = "Bugün: ${state.bugunGun}. Gün",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // İmsak / İftar
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                VaktKarti("İmsak", state.imsakVakti, GoldAccent, Modifier.weight(1f))
                VaktKarti("İftar", state.iftarVakti, IslamicGreen, Modifier.weight(1f))
            }

            // Geri Sayım
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (state.iftaraKaliyor) "İftara Kalan" else "İmsaka Kalan",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = state.kalonSure,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (state.iftaraKaliyor) IslamicGreen else GoldAccent
                    )
                }
            }

            // Bugünkü Takip
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "BUGÜNKÜ TAKIP",
                        style = MaterialTheme.typography.labelMedium,
                        color = GoldAccent
                    )
                    Spacer(Modifier.height(12.dp))
                    TakipSatiri(
                        label = "Oruç Tutuldu mu?",
                        checked = state.bugunOrucu,
                        onCheckedChange = viewModel::orucGuncelle
                    )
                    TakipSatiri(
                        label = "Teravih Kılındı mı?",
                        checked = state.bugunTeravih,
                        onCheckedChange = viewModel::teravihGuncelle
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Hatim Sayfası (bugün):", style = MaterialTheme.typography.bodyMedium)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.hatimGuncelle(maxOf(0, state.bugunHatim - 1)) },
                                modifier = Modifier.size(32.dp)
                            ) { Text("-") }
                            Text("${state.bugunHatim}", fontWeight = FontWeight.Bold, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                            IconButton(
                                onClick = { viewModel.hatimGuncelle(state.bugunHatim + 1) },
                                modifier = Modifier.size(32.dp)
                            ) { Text("+") }
                        }
                    }
                }
            }

            // 30 Gün Takvimi
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "30 GÜN TAKVİMİ",
                        style = MaterialTheme.typography.labelMedium,
                        color = GoldAccent
                    )
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(30) { gun ->
                            val gunData = state.gunler.find { it.gun == gun + 1 }
                            GunKutusu(
                                gun = gun + 1,
                                oruc = gunData?.orucTutuldu ?: false,
                                bugun = gun + 1 == state.bugunGun
                            )
                        }
                    }
                }
            }

            // İstatistikler
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RamazanStat("🌙", "${state.tutulanOruc}/30", "Oruç")
                    RamazanStat("🕌", "${state.kilinanTeravih}/30", "Teravih")
                    RamazanStat("📖", "${state.toplamHatimSayfa}/600", "Hatim Sayfa")
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun VaktKarti(baslik: String, vakit: String, renk: Color, modifier: Modifier = Modifier) {
    GlassCard(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(baslik, style = MaterialTheme.typography.labelLarge, color = renk)
            Text(vakit, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun TakipSatiri(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun GunKutusu(gun: Int, oruc: Boolean, bugun: Boolean) {
    val renk = when {
        bugun -> GoldAccent
        oruc  -> IslamicGreen
        else  -> MaterialTheme.colorScheme.surfaceVariant
    }
    Surface(
        modifier = Modifier.size(36.dp),
        shape = MaterialTheme.shapes.small,
        color = renk
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "$gun",
                style = MaterialTheme.typography.labelSmall,
                color = if (oruc || bugun) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RamazanStat(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 24.sp)
        Text(value, fontWeight = FontWeight.Bold, color = GoldAccent)
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun HilalCizimi(gun: Int, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val cx = size.width / 2
        val cy = size.height / 2
        val r = size.minDimension / 2 * 0.8f
        val progress = gun / 30f
        val path = Path().apply {
            moveTo(cx, cy - r)
            val sweep = 180f * progress + 10f
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(cx - r, cy - r, cx + r, cy + r),
                startAngleDegrees = -90f,
                sweepAngleDegrees = sweep,
                forceMoveTo = false
            )
        }
        drawPath(path, color = GoldAccent, style = Stroke(width = 4f))
        drawCircle(GoldAccent.copy(alpha = 0.3f), r * 0.2f, Offset(cx + r * 0.6f, cy - r * 0.4f))
    }
}
