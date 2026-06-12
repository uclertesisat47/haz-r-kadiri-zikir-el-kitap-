package com.kadiri.elkitabi.features.kitap.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.theme.ArabesqueGold
import com.kadiri.elkitabi.core.designsystem.theme.ArabicTextMedium
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.kitap.presentation.components.BolumKarti
import com.kadiri.elkitabi.features.kitap.presentation.components.FontSizeSlider
import com.kadiri.elkitabi.features.kitap.presentation.components.OkumaModuBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitapDetayScreen(
    kitapId: String,
    onBack: () -> Unit,
    viewModel: KitapViewModel = hiltViewModel()
) {
    val state       = viewModel.detayState.collectAsState().value
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope       = rememberCoroutineScope()

    LaunchedEffect(kitapId) { viewModel.loadKitap(kitapId) }

    ModalNavigationDrawer(
        drawerState   = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text     = "Bölümler",
                    style    = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(Spacing.md)
                )
                state.bolumler.forEach { bolum ->
                    BolumKarti(
                        bolum      = bolum,
                        isSelected = bolum == state.secilenBolum,
                        onClick    = {
                            viewModel.bolumSec(bolum)
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title          = { Text(state.kitap?.baslik ?: "") },
                    navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } },
                    actions = {
                        IconButton(onClick = { viewModel.arabicModToggle() }) {
                            Icon(Icons.Filled.Translate, "Görünüm", tint = if (state.isArabicMode) GoldAccent else MaterialTheme.colorScheme.onSurface)
                        }
                        val bolum = state.secilenBolum
                        val yerImiVar = bolum != null && state.yerImleri.any { it.bolumId == bolum.id }
                        IconButton(onClick = {
                            if (bolum != null && state.kitap != null) {
                                if (yerImiVar) viewModel.yerImiSil(state.yerImleri.first { it.bolumId == bolum.id }.id)
                                else viewModel.yerImiEkle(state.kitap.id, bolum.id, bolum.sayfa)
                            }
                        }) {
                            Icon(
                                if (yerImiVar) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                "Yer İmi",
                                tint = if (yerImiVar) GoldAccent else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Column {
                    FontSizeSlider(
                        boyut     = state.fontBoyutu,
                        onChange  = viewModel::fontBoyutuDegistir
                    )
                    OkumaModuBar(
                        bolumAdi        = state.secilenBolum?.baslik ?: "",
                        onBolumListeAc  = { scope.launch { drawerState.open() } },
                        onceki          = {
                            val idx = state.bolumler.indexOf(state.secilenBolum)
                            if (idx > 0) viewModel.bolumSec(state.bolumler[idx - 1])
                        },
                        sonraki         = {
                            val idx = state.bolumler.indexOf(state.secilenBolum)
                            if (idx < state.bolumler.lastIndex) viewModel.bolumSec(state.bolumler[idx + 1])
                        }
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(Spacing.md)
            ) {
                state.secilenBolum?.let { bolum ->
                    Text(
                        text  = bolum.arabicBaslik,
                        style = ArabicTextMedium.copy(fontSize = (state.fontBoyutu * 1.2f).sp),
                        color = ArabesqueGold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(Spacing.sm))
                    Text(
                        text  = bolum.baslik,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(Spacing.md))
                    Text(
                        text  = bolum.icerik,
                        style = if (state.isArabicMode)
                            ArabicTextMedium.copy(fontSize = state.fontBoyutu.sp)
                        else
                            MaterialTheme.typography.bodyLarge.copy(fontSize = state.fontBoyutu.sp),
                        lineHeight = (state.fontBoyutu * 1.8f).sp
                    )
                    Spacer(Modifier.height(Spacing.xxxl))
                }
            }
        }
    }
}
