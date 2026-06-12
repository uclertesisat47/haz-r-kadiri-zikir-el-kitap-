package com.kadiri.elkitabi.features.zekat.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZekatScreen(onBack: () -> Unit) {
    var altinGram by remember { mutableStateOf("") }
    var gumuzGram by remember { mutableStateOf("") }
    var nakit by remember { mutableStateOf("") }
    var ticaret by remember { mutableStateOf("") }
    var hisseler by remember { mutableStateOf("") }
    var kiraGeliri by remember { mutableStateOf("") }
    var borclar by remember { mutableStateOf("") }
    var altinFiyat by remember { mutableStateOf("2500") }

    var sonuc by remember { mutableStateOf<ZekatSonuc?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("💰 Zekât Hesaplayıcı") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("NİSAP DEĞERİ", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("1g Altın Fiyatı (₺):", style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            value = altinFiyat, onValueChange = { altinFiyat = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.width(120.dp), singleLine = true
                        )
                    }
                    val nisap = (altinFiyat.toDoubleOrNull() ?: 2500.0) * 85
                    Text("Nisap: ₺${"%,.0f".format(nisap)} (85g altın)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("VARLIKLAR", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                    ZekatInput("Altın (gram)", altinGram) { altinGram = it }
                    ZekatInput("Gümüş (gram)", gumuzGram) { gumuzGram = it }
                    ZekatInput("Nakit Para (₺)", nakit) { nakit = it }
                    ZekatInput("Ticaret Malı (₺)", ticaret) { ticaret = it }
                    ZekatInput("Hisseler/Yatırım (₺)", hisseler) { hisseler = it }
                    ZekatInput("Kira Geliri (₺)", kiraGeliri) { kiraGeliri = it }
                }
            }

            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("BORÇLAR", style = MaterialTheme.typography.labelMedium, color = Color(0xFFEF5350))
                    Spacer(Modifier.height(8.dp))
                    ZekatInput("Borçlarım (₺)", borclar) { borclar = it }
                }
            }

            Button(
                onClick = {
                    val fiyat = altinFiyat.toDoubleOrNull() ?: 2500.0
                    val toplamVarik = (altinGram.toDoubleOrNull() ?: 0.0) * fiyat +
                        (gumuzGram.toDoubleOrNull() ?: 0.0) * 30.0 +
                        (nakit.toDoubleOrNull() ?: 0.0) +
                        (ticaret.toDoubleOrNull() ?: 0.0) +
                        (hisseler.toDoubleOrNull() ?: 0.0) +
                        (kiraGeliri.toDoubleOrNull() ?: 0.0)
                    val toplamBorc = borclar.toDoubleOrNull() ?: 0.0
                    val net = toplamVarik - toplamBorc
                    val nisap = fiyat * 85
                    sonuc = ZekatSonuc(
                        net = net,
                        nisap = nisap,
                        yukumluMu = net >= nisap,
                        zekatMiktari = if (net >= nisap) net * 0.025 else 0.0
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zekât Hesapla")
            }

            sonuc?.let { s ->
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            if (s.yukumluMu) "✅ Zekât Yükümlüsüsünüz" else "ℹ️ Nisabın Altındasınız",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = if (s.yukumluMu) IslamicGreen else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (s.yukumluMu) {
                            Spacer(Modifier.height(8.dp))
                            Text("Ödenecek Zekât:", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                "₺${"%,.2f".format(s.zekatMiktari)}",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                            Text("(%2.5 × ₺${"%,.0f".format(s.net)})", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            Text("Net varlığınız (₺${"%,.0f".format(s.net)}) nisap değerinin (₺${"%,.0f".format(s.nisap)}) altındadır.", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ZekatInput(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        placeholder = { Text("0") }
    )
}

data class ZekatSonuc(
    val net: Double,
    val nisap: Double,
    val yukumluMu: Boolean,
    val zekatMiktari: Double
)
