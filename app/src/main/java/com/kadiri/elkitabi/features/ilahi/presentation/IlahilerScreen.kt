package com.kadiri.elkitabi.features.ilahi.presentation

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing

private data class Ilahi(
    val id: Int,
    val baslik: String,
    val sozler: String,
    val kategori: String
)

private val İLAHİ_LİSTESİ = listOf(
    Ilahi(1, "Salavât-ı Şerife", "Allahümme salli alâ seyyidinâ Muhammedin...", "Salavat"),
    Ilahi(2, "Yâ Nûru'l-Hudâ", "Yâ nûru'l-hudâ yâ habîba'llâh...", "Kadiri"),
    Ilahi(3, "Hû Diyelim", "Hû diyelim Hû diyelim / Allah diyelim Allah...", "Zikir"),
    Ilahi(4, "Lâ İlâhe İllallah", "Lâ ilâhe illallah / Muhammedü'r-Resûlullah...", "Zikir"),
    Ilahi(5, "Merhaba Yâ Mustafâ", "Merhaba ey şâh-ı enbiyâ merhaba / Merhaba ey kân-ı asfiyâ merhaba...", "Mevlid"),
    Ilahi(6, "Seninle Var Oldum", "Seninle var oldum seninle yükseldim / Senin cemâlinle her yerden geçtim...", "Kadiri"),
    Ilahi(7, "Hu Demektir Asıl İş", "Hu demektir asıl iş, cân içinde can gerek...", "Tasavvuf"),
    Ilahi(8, "Esselâmü Aleyke Yâ Resûlallah", "Es-selâmü aleyke yâ Resûlallah...", "Salavat"),
    Ilahi(9, "Kadiri Yolunu Tuttuk", "Kadiri yolunu tuttuk, Geylânî kıble edindik...", "Kadiri"),
    Ilahi(10, "Allah Allah İllallah", "Allah Allah illallah / Hep bir olalım Allah...", "Zikir"),
    Ilahi(11, "Yâ Rabbî Şükür", "Yâ Rabbî şükür sana, verdiğin nîmete...", "Hamd"),
    Ilahi(12, "Ramazan Geldi Yine", "Ramazan geldi yine, nûr doldu yine...", "Ramazan"),
    Ilahi(13, "Mevlânâ Hu", "Mevlânâ Hu Mevlânâ / Nûrullah Hu Nûrullah...", "Tasavvuf"),
    Ilahi(14, "Ne Büyüksün Yâ İlâhî", "Ne büyüksün yâ ilâhî, ne yücedir şânın...", "Hamd"),
    Ilahi(15, "Tövbe Yâ Rabbi", "Tövbe yâ Rabbi tövbe, günahkâr kuluna...", "Tövbe")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IlahilerScreen(onBack: () -> Unit) {
    var oynatilan by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("İlahiler") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            items(İLAHİ_LİSTESİ, key = { it.id }) { ilahi ->
                IslamicCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Spacing.sm)
                        .clickable { oynatilan = if (oynatilan == ilahi.id) null else ilahi.id }
                ) {
                    Row(
                        modifier          = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                        ) {
                            Icon(
                                Icons.Filled.MusicNote,
                                null,
                                tint     = if (oynatilan == ilahi.id) GoldAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(ilahi.baslik,   style = MaterialTheme.typography.titleSmall, color = if (oynatilan == ilahi.id) GoldAccent else MaterialTheme.colorScheme.onSurface)
                                Text(ilahi.kategori, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Icon(
                            imageVector = if (oynatilan == ilahi.id) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                            contentDescription = null,
                            tint = if (oynatilan == ilahi.id) IslamicGreen else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                if (oynatilan == ilahi.id) {
                    Text(
                        text     = ilahi.sozler,
                        style    = MaterialTheme.typography.bodySmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = Spacing.lg, bottom = Spacing.sm, end = Spacing.md)
                    )
                }
            }
            item { Spacer(Modifier.height(Spacing.xxxl)) }
        }
    }
}
