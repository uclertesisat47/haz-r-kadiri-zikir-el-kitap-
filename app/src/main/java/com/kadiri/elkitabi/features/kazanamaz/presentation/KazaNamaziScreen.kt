package com.kadiri.elkitabi.features.kazanamaz.presentation

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KazaNamaziScreen(
    onBack: () -> Unit,
    viewModel: KazaNamaziViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var baslangicYili by remember { mutableStateOf("") }
    var gunlukHedef by remember { mutableStateOf("1") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🕌 Kaza Namazı Takibi") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.kazaKaydi == null) {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("KAZA NAMAZI HESAPLA", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                        Text("Kaç yıldan beri namaz kılıyorsunuz?", style = MaterialTheme.typography.bodyMedium)
                        OutlinedTextField(
                            value = baslangicYili,
                            onValueChange = { baslangicYili = it },
                            label = { Text("Namaza Başladığım Yıl (örn: 2015)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = gunlukHedef,
                            onValueChange = { gunlukHedef = it },
                            label = { Text("Günlük Kaza Hedefi") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                        val yil = baslangicYili.toIntOrNull()
                        if (yil != null && yil < LocalDate.now().year) {
                            val gecenYil = LocalDate.now().year - yil
                            val toplamKaza = gecenYil * 365 * 5
                            Text("Tahmini toplam kaza: $toplamKaza vakit", color = GoldAccent, style = MaterialTheme.typography.bodySmall)
                        }
                        Button(
                            onClick = {
                                val yilInt = baslangicYili.toIntOrNull() ?: return@Button
                                val hedefInt = gunlukHedef.toIntOrNull() ?: 1
                                viewModel.kazaOlustur(yilInt, hedefInt)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("Hesapla ve Takibe Başla") }
                    }
                }
            } else {
                val kaza = state.kazaKaydi
                val ilerleme = if (kaza.toplamKaza > 0) kaza.tamamlanan.toFloat() / kaza.toplamKaza else 0f

                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("KAZA NAMAZI TAKİBİ", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "${kaza.tamamlanan}",
                            fontSize = 56.sp,
                            fontWeight = FontWeight.Bold,
                            color = IslamicGreen
                        )
                        Text("/ ${kaza.toplamKaza} vakit", style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(8.dp))
                        LinearProgressIndicator(progress = { ilerleme }, modifier = Modifier.fillMaxWidth())
                        Text("Tamamlanan: %${"%.1f".format(ilerleme * 100)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(12.dp))
                        Text("Kalan: ${kaza.toplamKaza - kaza.tamamlanan} vakit", style = MaterialTheme.typography.bodyMedium)
                        Text("Günlük Hedef: ${kaza.gunlukHedef} vakit", style = MaterialTheme.typography.bodySmall, color = GoldAccent)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { viewModel.kazaEkle(1) },
                        modifier = Modifier.weight(1f)
                    ) { Text("+1 Vakit") }
                    Button(
                        onClick = { viewModel.kazaEkle(kaza.gunlukHedef) },
                        modifier = Modifier.weight(1f)
                    ) { Text("+${kaza.gunlukHedef} Vakit (Hedef)") }
                }

                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("BİLGİ", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                        Spacer(Modifier.height(8.dp))
                        Text("Kaza namazları, kaçırılan farz namazları telafi etmek için kılınan namazlardır. Alimler, kaza namazlarını mümkün olan en kısa sürede kılmayı tavsiye eder.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
