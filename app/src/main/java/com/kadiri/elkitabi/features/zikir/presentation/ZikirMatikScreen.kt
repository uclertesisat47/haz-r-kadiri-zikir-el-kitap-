package com.kadiri.elkitabi.features.zikir.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.AnimatedCounter
import com.kadiri.elkitabi.core.designsystem.components.GeometricIslamicBackground
import com.kadiri.elkitabi.core.designsystem.components.IslamicPattern
import com.kadiri.elkitabi.core.designsystem.components.RadialProgressArc
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.SpringZikir
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.core.designsystem.theme.ZikirCounterStyle
import com.kadiri.elkitabi.core.designsystem.theme.ZikirNameStyle
import com.kadiri.elkitabi.core.designsystem.theme.ZikirBgGradient
import com.kadiri.elkitabi.features.zikir.presentation.components.ArabesqueRing
import com.kadiri.elkitabi.features.zikir.presentation.components.ZikirTamamModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZikirMatikScreen(
    onNavigateToIstatistik: () -> Unit,
    onNavigateToGecmis: () -> Unit,
    onNavigateToOzel: () -> Unit,
    viewModel: ZikirViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showSecimiSheet by remember { mutableStateOf(false) }
    var showAyarlarSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val tapScale by animateFloatAsState(
        targetValue   = if (state.mevcutSayi > 0 && state.mevcutSayi % 1 == 0) 0.95f else 1f,
        animationSpec = spring(stiffness = 600f),
        label         = "tap_scale"
    )
    val progress = if (state.hedef > 0) state.mevcutSayi.toFloat() / state.hedef else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(ZikirBgGradient))
    ) {
        GeometricIslamicBackground(
            modifier     = Modifier.fillMaxSize(),
            patternColor = GoldAccent.copy(alpha = 0.03f),
            patternType  = IslamicPattern.STAR_8
        )

        // Top action bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = Spacing.md, end = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { showAyarlarSheet = true }) {
                Icon(Icons.Filled.Settings, "Ayarlar", tint = GoldAccent.copy(alpha = 0.7f))
            }
            IconButton(onClick = onNavigateToGecmis) {
                Icon(Icons.Filled.History, "Geçmiş", tint = GoldAccent.copy(alpha = 0.7f))
            }
            IconButton(onClick = onNavigateToIstatistik) {
                Icon(Icons.Filled.BarChart, "İstatistik", tint = GoldAccent.copy(alpha = 0.7f))
            }
        }

        Column(
            modifier            = Modifier.fillMaxSize().padding(horizontal = Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Zikir adı (Arapça)
            state.seciliZikir?.let { zikir ->
                Text(
                    text      = zikir.arabicName,
                    style     = ZikirNameStyle.copy(color = GoldAccent),
                    modifier  = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) { detectTapGestures(onTap = { showSecimiSheet = true }) }
                )
                Text(
                    text      = zikir.turkishName,
                    style     = MaterialTheme.typography.titleMedium,
                    color     = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(Spacing.xxl))

            // Ana zikir dairesi
            Box(contentAlignment = Alignment.Center) {
                ArabesqueRing(
                    modifier  = Modifier.size(300.dp),
                    rotating  = state.isSessionActive
                )
                RadialProgressArc(
                    progress  = progress,
                    modifier  = Modifier.size(260.dp),
                    strokeWidth = 16f
                )
                Box(
                    modifier          = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.surface
                            )
                        ))
                        .scale(tapScale)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap    = {
                                    if (!state.isSessionActive) viewModel.oturumBaslat()
                                    viewModel.zikirSay()
                                },
                                onLongPress = { viewModel.sifirla() }
                            )
                        }
                        .semantics { contentDescription = "Zikir say — dokunun" },
                    contentAlignment  = Alignment.Center
                ) {
                    AnimatedCounter(
                        count = state.mevcutSayi,
                        style = ZikirCounterStyle,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(Spacing.lg))

            // Tur ve toplam
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${state.turSayisi}", style = MaterialTheme.typography.headlineMedium, color = GoldAccent)
                    Text("Tur", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${state.hedef}", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                    Text("Hedef", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${state.toplamSayi}", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onSurface)
                    Text("Toplam", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(Modifier.height(Spacing.xl))

            Text(
                text  = "Uzun basın → Sıfırla  |  Kaydır → Zikir seç",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                textAlign = TextAlign.Center
            )
        }

        // Tamamlama modal
        AnimatedVisibility(
            visible = state.tamamlandi,
            enter   = scaleIn() + fadeIn(),
            exit    = scaleOut() + fadeOut()
        ) {
            ZikirTamamModal(
                turSayisi    = state.turSayisi,
                zikirAdi     = state.seciliZikir?.turkishName ?: "",
                onDismiss    = { }
            )
        }
    }

    // Zikir seçim BottomSheet
    if (showSecimiSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSecimiSheet = false },
            sheetState       = sheetState,
            containerColor   = MaterialTheme.colorScheme.surface
        ) {
            ZikirSecimiSheet(
                zikirListesi = state.zikirListesi,
                seciliZikir  = state.seciliZikir,
                onZikirSec   = { zikir ->
                    viewModel.zikirSec(zikir)
                    showSecimiSheet = false
                },
                onOzelEkle   = onNavigateToOzel
            )
        }
    }

    // Ayarlar BottomSheet
    if (showAyarlarSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAyarlarSheet = false },
            sheetState       = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor   = MaterialTheme.colorScheme.surface
        ) {
            ZikirAyarlarSheet(
                seciliSes       = state.seciliSes,
                vibrasyonAktif  = state.vibrasyonAktif,
                onSesChange     = viewModel::setSes,
                onVibrasyonChange = viewModel::setVibrasyon,
                onDismiss       = { showAyarlarSheet = false }
            )
        }
    }
}
