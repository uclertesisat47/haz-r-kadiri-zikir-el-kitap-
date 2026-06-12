package com.kadiri.elkitabi.features.kitap.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.kitap.domain.model.Kitap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitapListScreen(
    onBack: () -> Unit,
    onKitapSec: (String) -> Unit,
    viewModel: KitapViewModel = hiltViewModel()
) {
    val state by viewModel.listState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("El Kitapları") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            items(state.kitaplar, key = { it.id }) { kitap ->
                KitapListKarti(
                    kitap    = kitap,
                    modifier = Modifier.padding(bottom = Spacing.sm),
                    onClick  = { onKitapSec(kitap.id) }
                )
            }
            item { Spacer(Modifier.height(Spacing.xxxl)) }
        }
    }
}

@Composable
private fun KitapListKarti(kitap: Kitap, modifier: Modifier, onClick: () -> Unit) {
    IslamicCard(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(
            modifier          = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.AutoStories, null, tint = GoldAccent, modifier = Modifier.padding(end = Spacing.sm))
            Column(modifier = Modifier.weight(1f)) {
                Text(kitap.baslik,       style = MaterialTheme.typography.titleMedium)
                Text(kitap.arabicBaslik, style = MaterialTheme.typography.bodySmall, color = ArabesqueGold)
                Text(kitap.aciklama,     style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
                if (kitap.toplamBolum > 0) {
                    Spacer(Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress     = { kitap.ilerlemeYuzdesi },
                        modifier     = Modifier.fillMaxWidth(),
                        color        = IslamicGreen,
                        trackColor   = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Text("${kitap.okunmusBolum}/${kitap.toplamBolum} bölüm", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.width(Spacing.sm))
            Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
