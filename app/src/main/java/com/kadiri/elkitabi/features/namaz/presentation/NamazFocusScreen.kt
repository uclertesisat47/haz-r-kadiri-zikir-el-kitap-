package com.kadiri.elkitabi.features.namaz.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadiri.elkitabi.core.designsystem.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NamazFocusScreen(onBack: () -> Unit) {
    var secilenRekat by remember { mutableStateOf(4) }
    var aktifRekat by remember { mutableIntStateOf(0) }
    var namazBasladi by remember { mutableStateOf(false) }
    var saniyeSayaci by remember { mutableIntStateOf(0) }
    var bitti by remember { mutableStateOf(false) }

    val secenekler = listOf(
        "Sabah Farzı (2 Rekat)" to 2,
        "Sabah Sünneti (2 Rekat)" to 2,
        "Öğle Farzı (4 Rekat)" to 4,
        "Öğle Sünneti (4 Rekat)" to 4,
        "İkindi Farzı (4 Rekat)" to 4,
        "Akşam Farzı (3 Rekat)" to 3,
        "Akşam Sünneti (2 Rekat)" to 2,
        "Yatsı Farzı (4 Rekat)" to 4,
        "Vitir Namazı (3 Rekat)" to 3,
    )

    LaunchedEffect(namazBasladi) {
        if (namazBasladi) {
            while (aktifRekat <= secilenRekat && !bitti) {
                delay(1000)
                saniyeSayaci++
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "cevher")
    val rotasyon by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing)),
        label = "rotasyon"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🕌 Namaz Focus Modu") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF080A09), Color(0xFF0F3D2C))))
                .padding(padding)
        ) {
            if (bitti) {
                BitisEkrani(
                    sure = saniyeSayaci,
                    rekat = secilenRekat,
                    onTekrar = { bitti = false; aktifRekat = 0; saniyeSayaci = 0; namazBasladi = false },
                    onGeri = onBack
                )
            } else if (!namazBasladi) {
                BaslangicEkrani(
                    secenekler = secenekler,
                    onBasla = { rekat ->
                        secilenRekat = rekat
                        namazBasladi = true
                        aktifRekat = 1
                    }
                )
            } else {
                FocusEkrani(
                    aktifRekat = aktifRekat,
                    toplamRekat = secilenRekat,
                    sure = saniyeSayaci,
                    rotasyon = rotasyon,
                    onRekatTamamla = {
                        if (aktifRekat < secilenRekat) aktifRekat++
                        else bitti = true
                    }
                )
            }
        }
    }
}

@Composable
private fun BaslangicEkrani(secenekler: List<Pair<String, Int>>, onBasla: (Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Hangi Namazı Kılacaksınız?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
        Spacer(Modifier.height(8.dp))
        secenekler.forEach { (isim, rekat) ->
            OutlinedButton(
                onClick = { onBasla(rekat) },
                modifier = Modifier.fillMaxWidth()
            ) { Text(isim, color = MaterialTheme.colorScheme.onSurface) }
        }
    }
}

@Composable
private fun FocusEkrani(
    aktifRekat: Int, toplamRekat: Int, sure: Int,
    rotasyon: Float, onRekatTamamla: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Arabesk halka animasyonu
        Canvas(modifier = Modifier.size(220.dp)) {
            val cx = size.width / 2; val cy = size.height / 2; val r = size.minDimension / 2 * 0.85f
            repeat(8) { i ->
                val a = Math.toRadians((i * 45.0) + rotasyon)
                val rx = cx + r * 0.6f * Math.cos(a).toFloat()
                val ry = cy + r * 0.6f * Math.sin(a).toFloat()
                drawCircle(GoldAccent.copy(alpha = 0.4f), 8f, Offset(rx, ry))
            }
            drawArc(
                color = GoldAccent,
                startAngle = -90f,
                sweepAngle = aktifRekat.toFloat() / toplamRekat * 360f,
                useCenter = false,
                topLeft = Offset(cx - r, cy - r),
                size = Size(r * 2, r * 2),
                style = Stroke(width = 8f)
            )
            drawCircle(GoldAccent.copy(alpha = 0.1f), r)
        }
        Spacer(Modifier.height(24.dp))
        Text("$aktifRekat / $toplamRekat", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
        Text("REKAT", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(8.dp))
        Text(formatSure(sure), fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(32.dp))
        NamazAdimGostergesi(aktifRekat = aktifRekat)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onRekatTamamla,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text(
                text = if (aktifRekat < toplamRekat) "Rekatı Tamamla ↓" else "Namazı Tamamla ✓",
                fontSize = 16.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun NamazAdimGostergesi(aktifRekat: Int) {
    val adimlar = listOf("Kıyam", "Rükü", "Secde 1", "Celse", "Secde 2")
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        adimlar.forEach { adim ->
            Surface(
                color = GoldAccent.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    adim,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldAccent
                )
            }
        }
    }
}

@Composable
private fun BitisEkrani(sure: Int, rekat: Int, onTekrar: () -> Unit, onGeri: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🕌", fontSize = 72.sp)
        Spacer(Modifier.height(16.dp))
        Text("Namaz Tamamlandı!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = GoldAccent)
        Spacer(Modifier.height(8.dp))
        Text("$rekat Rekat — ${formatSure(sure)}", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(32.dp))
        Text("\"الصَّلَاةُ عِمَادُ الدِّينِ\"", fontSize = 20.sp, color = GoldAccent)
        Text("Namaz dinin direğidir.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(40.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onGeri) { Text("Bitir") }
            Button(onClick = onTekrar) { Text("Tekrar") }
        }
    }
}

private fun formatSure(saniye: Int) = "%02d:%02d".format(saniye / 60, saniye % 60)
