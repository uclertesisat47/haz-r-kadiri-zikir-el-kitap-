package com.kadiri.elkitabi.features.planlayici.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GunlukPlanScreen(
    onBack: () -> Unit,
    viewModel: GunlukPlanViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⏰ Günün İbadet Takvimi") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                GlassCard(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("BUGÜNKÜ TAMAMLANMA", style = MaterialTheme.typography.labelMedium, color = GoldAccent)
                        Spacer(Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { if (state.toplamSayi > 0) state.tamamlananSayi.toFloat() / state.toplamSayi else 0f },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            "${state.tamamlananSayi} / ${state.toplamSayi} tamamlandı",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            items(state.planlar) { plan ->
                PlanKarti(
                    plan = plan,
                    onTamamla = { viewModel.planTamamla(plan.id, !plan.tamamlandi) }
                )
            }

            item {
                Text("GÜNLÜK PROGRAM", style = MaterialTheme.typography.labelMedium, color = GoldAccent, modifier = Modifier.padding(top = 8.dp))
            }

            items(state.namazProgrami) { blok ->
                ZamanBlokuKarti(blok = blok)
            }

            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}

@Composable
private fun PlanKarti(plan: PlanUiModel, onTamamla: () -> Unit) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Checkbox(
                checked = plan.tamamlandi,
                onCheckedChange = { onTamamla() }
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    plan.ibadetAdi,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    plan.saat,
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldAccent
                )
            }
            if (plan.ozelMi) {
                AssistChip(onClick = {}, label = { Text("Özel", style = MaterialTheme.typography.labelSmall) })
            }
        }
    }
}

@Composable
private fun ZamanBlokuKarti(blok: ZamanBloku) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                color = if (blok.namazVakti) GoldAccent else MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.width(60.dp)
            ) {
                Text(
                    blok.saat,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(4.dp),
                    color = if (blok.namazVakti) Color.Black else MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Column {
            Text(
                blok.baslik,
                fontWeight = if (blok.namazVakti) FontWeight.Bold else FontWeight.Normal,
                color = if (blok.namazVakti) GoldAccent else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall
            )
            blok.oneriler.forEach { onerı ->
                Text(
                    "↕ $onerı",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                )
            }
        }
    }
}
