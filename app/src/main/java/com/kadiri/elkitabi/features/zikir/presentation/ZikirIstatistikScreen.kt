package com.kadiri.elkitabi.features.zikir.presentation

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.core.utils.DateUtils
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirIstatistik
import com.kadiri.elkitabi.features.zikir.presentation.components.IstatistikGrafikler
import com.kadiri.elkitabi.features.zikir.presentation.components.StreakWidget
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZikirIstatistikScreen(
    onBack: () -> Unit,
    viewModel: ZikirViewModel = hiltViewModel()
) {
    var istatistik by remember { mutableStateOf<ZikirIstatistik?>(null) }

    LaunchedEffect(Unit) {
        // ViewModel üzerinden istatistik yükle
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title  = { Text("Zikir İstatistikleri") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Geri")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
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
            Spacer(Modifier.height(Spacing.sm))

            // Özet kartları
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                IstatistikKarti(
                    modifier = Modifier.weight(1f),
                    baslik   = "Bugün",
                    deger    = istatistik?.bugunSayim?.toString() ?: "—"
                )
                IstatistikKarti(
                    modifier = Modifier.weight(1f),
                    baslik   = "Bu Hafta",
                    deger    = istatistik?.haftaSayim?.toString() ?: "—"
                )
                IstatistikKarti(
                    modifier = Modifier.weight(1f),
                    baslik   = "Bu Ay",
                    deger    = istatistik?.aySayim?.toString() ?: "—"
                )
            }

            Spacer(Modifier.height(Spacing.md))

            IslamicCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Toplam Zikir", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(istatistik?.toplamSayim?.toString() ?: "—", style = MaterialTheme.typography.headlineMedium, color = GoldAccent)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("En Çok", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(istatistik?.enCokZikir ?: "—", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }

            Spacer(Modifier.height(Spacing.md))

            StreakWidget(streak = istatistik?.streak ?: 0)

            Spacer(Modifier.height(Spacing.md))

            istatistik?.let { ist ->
                IstatistikGrafikler(
                    haftalikData = ist.haftalikData,
                    aylikData    = ist.aylikData
                )
            }

            Spacer(Modifier.height(Spacing.xxxl))
        }
    }
}

@Composable
private fun IstatistikKarti(modifier: Modifier, baslik: String, deger: String) {
    IslamicCard(modifier = modifier, contentPadding = 12.dp) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(deger,  style = MaterialTheme.typography.titleLarge,  color = GoldAccent)
            Text(baslik, style = MaterialTheme.typography.labelSmall,  color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
