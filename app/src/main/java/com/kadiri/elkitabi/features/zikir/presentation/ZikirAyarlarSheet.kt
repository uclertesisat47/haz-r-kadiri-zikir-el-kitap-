package com.kadiri.elkitabi.features.zikir.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirSesi

@Composable
fun ZikirAyarlarSheet(
    seciliSes: ZikirSesi,
    vibrasyonAktif: Boolean,
    onSesChange: (ZikirSesi) -> Unit,
    onVibrasyonChange: (Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md)) {
        Text("Zikir Ayarları", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(Spacing.md))

        Text("Ses Modu", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(Spacing.sm))

        ZikirSesi.entries.forEach { ses ->
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text     = when (ses) {
                        ZikirSesi.SESSIZ     -> "Sessiz"
                        ZikirSesi.TITREŞIM   -> "Titreşim"
                        ZikirSesi.TEBİH_SESİ -> "Tebih Sesi"
                        ZikirSesi.NEY_SESİ   -> "Ney Sesi"
                        ZikirSesi.SU_SESİ    -> "Su Sesi"
                    },
                    style    = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked         = ses == seciliSes,
                    onCheckedChange = { if (it) onSesChange(ses) },
                    colors          = SwitchDefaults.colors(checkedTrackColor = GoldAccent)
                )
            }
            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
        }

        Spacer(Modifier.height(Spacing.md))
        Text("Titreşim", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Her zikir sayımında titreşim", modifier = Modifier.weight(1f))
            Switch(
                checked         = vibrasyonAktif,
                onCheckedChange = onVibrasyonChange,
                colors          = SwitchDefaults.colors(checkedTrackColor = GoldAccent)
            )
        }
        Spacer(Modifier.height(Spacing.xxxl))
    }
}

private val Float.dp get() = androidx.compose.ui.unit.Dp(this)
