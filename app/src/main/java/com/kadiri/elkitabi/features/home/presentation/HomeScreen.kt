package com.kadiri.elkitabi.features.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.BismillahWidget
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.home.presentation.components.DailyZikirCard
import com.kadiri.elkitabi.features.home.presentation.components.FeatureGrid
import com.kadiri.elkitabi.features.home.presentation.components.HijriDateWidget
import com.kadiri.elkitabi.features.home.presentation.components.PrayerTimeWidget

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToKitap: () -> Unit,
    onNavigateToZikir: () -> Unit,
    onNavigateToDua: () -> Unit,
    onNavigateToNamaz: () -> Unit,
    onNavigateToKible: () -> Unit,
    onNavigateToEsma: () -> Unit,
    onNavigateToSilsile: () -> Unit,
    onNavigateToTakvim: () -> Unit,
    onNavigateToIlahi: () -> Unit,
    onNavigateToAmel: () -> Unit,
    onNavigateToAyarlar: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text  = "Kadiri Tarikatı",
                        style = MaterialTheme.typography.titleLarge
                    )
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
                .padding(horizontal = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Spacer(Modifier.height(Spacing.sm))

            BismillahWidget(modifier = Modifier.fillMaxWidth())

            HijriDateWidget(
                hijriDate = state.hijriDate,
                miladi    = state.miladi,
                modifier  = Modifier.fillMaxWidth()
            )

            PrayerTimeWidget(
                nextPrayer     = state.nextPrayer,
                nextPrayerTime = state.nextPrayerTime,
                remainingTime  = state.remainingTime,
                modifier       = Modifier.fillMaxWidth(),
                onClick        = onNavigateToNamaz
            )

            DailyZikirCard(
                count    = state.todayZikirCount,
                streak   = state.streak,
                modifier = Modifier.fillMaxWidth(),
                onClick  = onNavigateToZikir
            )

            FeatureGrid(
                onNavigateToKitap   = onNavigateToKitap,
                onNavigateToZikir   = onNavigateToZikir,
                onNavigateToDua     = onNavigateToDua,
                onNavigateToNamaz   = onNavigateToNamaz,
                onNavigateToKible   = onNavigateToKible,
                onNavigateToEsma    = onNavigateToEsma,
                onNavigateToSilsile = onNavigateToSilsile,
                onNavigateToTakvim  = onNavigateToTakvim,
                onNavigateToIlahi   = onNavigateToIlahi,
                onNavigateToAmel    = onNavigateToAmel
            )

            Spacer(Modifier.height(Spacing.xxxl))
        }
    }
}
