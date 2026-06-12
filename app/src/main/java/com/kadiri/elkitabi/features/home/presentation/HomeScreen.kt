package com.kadiri.elkitabi.features.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.BismillahWidget
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.home.presentation.components.*

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
    onNavigateToBahce: () -> Unit = {},
    onNavigateToMoodDua: () -> Unit = {},
    onNavigateToRamazan: () -> Unit = {},
    onNavigateToGunluk: () -> Unit = {},
    onNavigateToQuiz: () -> Unit = {},
    onNavigateToPlanlayici: () -> Unit = {},
    onNavigateToKuran: () -> Unit = {},
    onNavigateToSiyer: () -> Unit = {},
    onNavigateToZekat: () -> Unit = {},
    onNavigateToVird: () -> Unit = {},
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
                onNavigateToKitap      = onNavigateToKitap,
                onNavigateToZikir      = onNavigateToZikir,
                onNavigateToDua        = onNavigateToDua,
                onNavigateToNamaz      = onNavigateToNamaz,
                onNavigateToKible      = onNavigateToKible,
                onNavigateToEsma       = onNavigateToEsma,
                onNavigateToSilsile    = onNavigateToSilsile,
                onNavigateToTakvim     = onNavigateToTakvim,
                onNavigateToIlahi      = onNavigateToIlahi,
                onNavigateToAmel       = onNavigateToAmel,
                onNavigateToBahce      = onNavigateToBahce,
                onNavigateToMoodDua    = onNavigateToMoodDua,
                onNavigateToRamazan    = onNavigateToRamazan,
                onNavigateToGunluk     = onNavigateToGunluk,
                onNavigateToQuiz       = onNavigateToQuiz,
                onNavigateToPlanlayici = onNavigateToPlanlayici,
                onNavigateToKuran      = onNavigateToKuran,
                onNavigateToSiyer      = onNavigateToSiyer,
                onNavigateToZekat      = onNavigateToZekat,
                onNavigateToVird       = onNavigateToVird,
                modifier               = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(Spacing.xxxl))
        }
    }
}
