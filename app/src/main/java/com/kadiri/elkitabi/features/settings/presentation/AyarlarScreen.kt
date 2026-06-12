package com.kadiri.elkitabi.features.settings.presentation

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
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyarlarScreen(
    onBack: () -> Unit,
    viewModel: AyarlarViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ayarlar") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") }
                }
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
            Text("Görünüm", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(Spacing.sm))

            AyarRow(
                icon   = Icons.Filled.DarkMode,
                baslik = "Koyu Tema"
            ) {
                Switch(
                    checked         = state.isDarkMode,
                    onCheckedChange = viewModel::setDarkMode,
                    colors          = SwitchDefaults.colors(checkedTrackColor = GoldAccent)
                )
            }
            HorizontalDivider()

            AyarRow(
                icon   = Icons.Filled.TextFields,
                baslik = "Yazı Boyutu (${state.fontSize.toInt()}sp)"
            ) {
                Slider(
                    value         = state.fontSize,
                    onValueChange = viewModel::setFontSize,
                    valueRange    = 12f..28f,
                    modifier      = Modifier.fillMaxWidth(0.5f),
                    colors        = SliderDefaults.colors(thumbColor = GoldAccent, activeTrackColor = GoldAccent)
                )
            }
            HorizontalDivider()

            Spacer(Modifier.height(Spacing.md))
            Text("Bildirimler", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(Spacing.sm))

            AyarRow(
                icon   = Icons.Filled.Notifications,
                baslik = "Namaz Vakti Bildirimleri"
            ) {
                Switch(
                    checked         = state.notificationsEnabled,
                    onCheckedChange = viewModel::setNotifications,
                    colors          = SwitchDefaults.colors(checkedTrackColor = GoldAccent)
                )
            }
            HorizontalDivider()

            Spacer(Modifier.height(Spacing.md))
            Text("Uygulama Hakkında", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(Spacing.sm))
            Text("Kadiri Tarikatı El Kitabı", style = MaterialTheme.typography.bodyMedium)
            Text("Sürüm 2.0.0", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(Modifier.height(Spacing.xxxl))
        }
    }
}

@Composable
private fun AyarRow(
    icon: ImageVector,
    baslik: String,
    content: @Composable () -> Unit
) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = GoldAccent, modifier = Modifier.padding(end = Spacing.sm))
        Text(baslik, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        content()
    }
}
