package com.kadiri.elkitabi.features.zikir.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.ArabicTextMedium
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.zikir.domain.model.Zikir
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirKategori

@Composable
fun ZikirSecimiSheet(
    zikirListesi: List<Zikir>,
    seciliZikir: Zikir?,
    onZikirSec: (Zikir) -> Unit,
    onOzelEkle: () -> Unit
) {
    var secilenKategori by remember { mutableStateOf<ZikirKategori?>(null) }

    val filtreliListe = if (secilenKategori == null) zikirListesi
    else zikirListesi.filter { it.kategori == secilenKategori }

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.md)) {
        Text(
            text  = "Zikir Seç",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = Spacing.sm)
        )

        // Kategori filtreleri
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            contentPadding        = PaddingValues(bottom = Spacing.sm)
        ) {
            item {
                FilterChip(
                    selected = secilenKategori == null,
                    onClick  = { secilenKategori = null },
                    label    = { Text("Tümü") },
                    colors   = FilterChipDefaults.filterChipColors(selectedContainerColor = GoldAccent.copy(alpha = 0.2f))
                )
            }
            item {
                FilterChip(
                    selected = secilenKategori == ZikirKategori.GENEL,
                    onClick  = { secilenKategori = ZikirKategori.GENEL },
                    label    = { Text("Genel") },
                    colors   = FilterChipDefaults.filterChipColors(selectedContainerColor = GoldAccent.copy(alpha = 0.2f))
                )
            }
            item {
                FilterChip(
                    selected = secilenKategori == ZikirKategori.KADİRİ_OZEL,
                    onClick  = { secilenKategori = ZikirKategori.KADİRİ_OZEL },
                    label    = { Text("Kadiri Özel") },
                    colors   = FilterChipDefaults.filterChipColors(selectedContainerColor = GoldAccent.copy(alpha = 0.2f))
                )
            }
            item {
                FilterChip(
                    selected = secilenKategori == ZikirKategori.ESMA_UL_HUSNA,
                    onClick  = { secilenKategori = ZikirKategori.ESMA_UL_HUSNA },
                    label    = { Text("Esma'ül Hüsna") },
                    colors   = FilterChipDefaults.filterChipColors(selectedContainerColor = GoldAccent.copy(alpha = 0.2f))
                )
            }
            item {
                FilterChip(
                    selected = secilenKategori == ZikirKategori.OZEL,
                    onClick  = { secilenKategori = ZikirKategori.OZEL },
                    label    = { Text("Özel") },
                    colors   = FilterChipDefaults.filterChipColors(selectedContainerColor = GoldAccent.copy(alpha = 0.2f))
                )
            }
        }

        HorizontalDivider()

        LazyColumn(
            modifier       = Modifier.fillMaxWidth().height(420.dp),
            contentPadding = PaddingValues(vertical = Spacing.sm)
        ) {
            items(filtreliListe, key = { it.id }) { zikir ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onZikirSec(zikir) }
                        .padding(vertical = Spacing.sm, horizontal = Spacing.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text  = zikir.turkishName,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (zikir == seciliZikir) GoldAccent else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text  = "Hedef: ${zikir.varsayilanHedef}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text  = zikir.arabicName.take(20),
                        style = ArabicTextMedium.copy(fontSize = 16.sp),
                        color = ArabesqueGold
                    )
                }
                HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            }
        }

        Spacer(Modifier.height(Spacing.sm))
        OutlinedButton(
            onClick  = onOzelEkle,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Filled.Add, "Özel Zikir Ekle")
            Text(" Özel Zikir Ekle")
        }
        Spacer(Modifier.height(Spacing.xxl))
    }
}

private val Int.sp get() = this.toFloat().let { androidx.compose.ui.unit.TextUnit(it, androidx.compose.ui.unit.TextUnitType.Sp) }
