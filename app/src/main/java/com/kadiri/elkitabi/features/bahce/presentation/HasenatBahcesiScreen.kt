package com.kadiri.elkitabi.features.bahce.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HasenatBahcesiScreen(
    onBack: () -> Unit,
    viewModel: HasenatBahcesiViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val infiniteTransition = rememberInfiniteTransition(label = "wind")
    val windAngle by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wind_angle"
    )

    val hour = LocalTime.now().hour
    val bgGradient = when {
        hour in 5..7   -> listOf(Color(0xFFFF8F00), Color(0xFF1B4D3E))
        hour in 8..17  -> listOf(Color(0xFF0D1B2A), Color(0xFF1B4D3E))
        hour in 18..20 -> listOf(Color(0xFF3D0060), Color(0xFFB8961E))
        else           -> listOf(Color(0xFF080A09), Color(0xFF0D100E))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hasenât Bahçesi 🌸", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(bgGradient))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Bahçe Canvas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0A2518))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawGarden(state.bitkiler, windAngle)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hasenât Skoru
            GlassCard(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "✨ Toplam Hasenât",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GoldAccent
                    )
                    Text(
                        text = "${state.totalHasenat}",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        StatItem("🌿", "${state.saglikliSayi}", "Sağlıklı Bitki")
                        StatItem("🔥", "${state.streak}", "Gün Streak")
                        StatItem("🌱", "${state.bitkiler.size}", "Toplam Bitki")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Bitki Ekle
            Text(
                text = "YENİ BİTKİ EKLE",
                style = MaterialTheme.typography.labelMedium,
                color = GoldAccent,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            BitkiTur.entries.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { tur ->
                        GlassCard(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Filled.LocalFlorist,
                                    contentDescription = null,
                                    tint = tur.renk,
                                    modifier = Modifier.size(20.dp)
                                )
                                Column {
                                    Text(
                                        text = tur.isim,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    FilledTonalButton(
                                        onClick = { viewModel.bitkiEkle(tur) },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.height(24.dp)
                                    ) {
                                        Text("Ekle", fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                    if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                }
            }

            if (state.bitkiler.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "BAHÇENİZ",
                    style = MaterialTheme.typography.labelMedium,
                    color = GoldAccent,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                state.bitkiler.forEach { bitki ->
                    BitkiKarti(
                        bitki = bitki,
                        onSula = { viewModel.bitkiSula(bitki.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun StatItem(emoji: String, value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = emoji, fontSize = 24.sp)
        Text(text = value, fontWeight = FontWeight.Bold, color = GoldAccent)
        Text(text = label, style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun BitkiKarti(
    bitki: BitkiUiModel,
    onSula: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassCard(modifier = modifier) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val saglikColor = when {
                bitki.saglik > 60 -> IslamicGreen
                bitki.saglik > 30 -> WarmAmber
                else -> Color(0xFFFF5252)
            }
            Icon(Icons.Filled.LocalFlorist, null, tint = saglikColor, modifier = Modifier.size(32.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = bitki.tur, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "Sağlık: ${bitki.saglik}/100 • Seviye ${bitki.seviye}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                LinearProgressIndicator(
                    progress = { bitki.saglik / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    color = saglikColor
                )
            }
            FilledTonalButton(
                onClick = onSula,
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("💧 Sula")
            }
        }
    }
}

private fun DrawScope.drawGarden(bitkiler: List<BitkiUiModel>, windAngle: Float) {
    // Zemin
    val toprakRenk = if (bitkiler.sumOf { it.saglik } > bitkiler.size * 50)
        Color(0xFF2E7D52) else Color(0xFF8D6E63)
    drawRect(
        color = toprakRenk,
        topLeft = Offset(0f, size.height * 0.7f),
        size = androidx.compose.ui.geometry.Size(size.width, size.height * 0.3f)
    )
    // Yıldızlar (gece)
    repeat(20) { i ->
        drawCircle(Color.White.copy(alpha = 0.6f), 2f,
            Offset((i * 53f) % size.width, (i * 37f) % (size.height * 0.65f)))
    }
    // Bitkiler
    bitkiler.take(8).forEachIndexed { idx, bitki ->
        val x = 50f + (idx % 4) * (size.width / 4f) + 20f
        val groundY = size.height * 0.7f
        val saglikColor = when {
            bitki.saglik > 60 -> Color(0xFF4CAF50)
            bitki.saglik > 30 -> Color(0xFFFFC107)
            else -> Color(0xFF9E9E9E)
        }
        drawBitki(x, groundY, bitki.seviye, saglikColor, windAngle)
    }
}

private fun DrawScope.drawBitki(
    x: Float, groundY: Float, seviye: Int,
    renk: Color, windAngle: Float
) {
    val sapYukseklik = 20f + seviye * 15f
    val sapPath = Path().apply {
        moveTo(x, groundY)
        val kontrol = Offset(x + windAngle * 3f, groundY - sapYukseklik * 0.5f)
        quadraticTo(kontrol.x, kontrol.y, x + windAngle * 2f, groundY - sapYukseklik)
    }
    drawPath(sapPath, color = Color(0xFF388E3C), style = Stroke(width = 3f))
    // Çiçek
    if (seviye >= 3) {
        val cx = x + windAngle * 2f
        val cy = groundY - sapYukseklik
        repeat(5) { i ->
            val angle = i * 72.0
            val rx = cx + (10f * Math.cos(Math.toRadians(angle))).toFloat()
            val ry = cy + (10f * Math.sin(Math.toRadians(angle))).toFloat()
            drawCircle(renk.copy(alpha = 0.8f), 6f, Offset(rx, ry))
        }
        drawCircle(Color(0xFFFFC107), 5f, Offset(cx, cy))
    } else {
        drawCircle(renk, 8f, Offset(x + windAngle, groundY - sapYukseklik))
    }
}
