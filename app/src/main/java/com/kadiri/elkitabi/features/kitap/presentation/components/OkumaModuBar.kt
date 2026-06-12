package com.kadiri.elkitabi.features.kitap.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

@Composable
fun OkumaModuBar(
    bolumAdi: String,
    onBolumListeAc: () -> Unit,
    onceki: () -> Unit,
    sonraki: () -> Unit
) {
    Surface(
        color    = MaterialTheme.colorScheme.surface,
        tonalElevation = androidx.compose.ui.unit.Dp(2f)
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.sm, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBolumListeAc) { Icon(Icons.Filled.List, "Bölümler") }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onceki) { Icon(Icons.Filled.ChevronLeft, "Önceki") }
                Text(
                    text     = bolumAdi,
                    style    = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = Spacing.sm)
                )
                IconButton(onClick = sonraki) { Icon(Icons.Filled.ChevronRight, "Sonraki") }
            }
        }
    }
}

private val Int.dp get() = androidx.compose.ui.unit.Dp(this.toFloat())
