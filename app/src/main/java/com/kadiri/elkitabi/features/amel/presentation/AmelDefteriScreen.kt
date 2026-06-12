package com.kadiri.elkitabi.features.amel.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.components.IslamicCard
import com.kadiri.elkitabi.core.designsystem.theme.GoldAccent
import com.kadiri.elkitabi.core.designsystem.theme.IslamicGreen
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.amel.data.local.entities.AmelEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmelDefteriScreen(
    onBack: () -> Unit,
    viewModel: AmelDefteriViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val sheetState = rememberModalBottomSheetState()
    val scope      = rememberCoroutineScope()
    var showSheet  by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Amel Defteri") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Geri") } }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet = true }, containerColor = GoldAccent) {
                Icon(Icons.Filled.Add, "Amel Ekle")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier       = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm)
        ) {
            item {
                Text("Bugün", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = Spacing.sm))
            }
            items(state.bugunAmeller, key = { it.id }) { amel ->
                AmelKarti(
                    amel       = amel,
                    onTamamla  = { viewModel.amelTamamla(amel.id) },
                    onSil      = { viewModel.amelSil(amel.id) }
                )
            }
            item {
                if (state.bugunAmeller.isEmpty()) {
                    Text(
                        "Bugün için henüz amel yok.\n+ ile yeni amel ekleyin.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(Spacing.md)
                    )
                }
                Spacer(Modifier.height(Spacing.xxxl))
            }
        }

        if (showSheet) {
            AmelEkleSheet(
                sheetState = sheetState,
                onDismiss  = { showSheet = false },
                onEkle     = { baslik, tur ->
                    viewModel.amelEkle(baslik, tur)
                    scope.launch { sheetState.hide() }.invokeOnCompletion { showSheet = false }
                }
            )
        }
    }
}

@Composable
private fun AmelKarti(amel: AmelEntity, onTamamla: () -> Unit, onSil: () -> Unit) {
    IslamicCard(modifier = Modifier.fillMaxWidth().padding(bottom = Spacing.sm)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(amel.baslik,   style = MaterialTheme.typography.titleSmall, color = if (amel.tamamlandi) IslamicGreen else MaterialTheme.colorScheme.onSurface)
                Text(amel.amelTuru, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row {
                if (!amel.tamamlandi) {
                    IconButton(onClick = onTamamla) { Icon(Icons.Filled.Check, "Tamamla", tint = IslamicGreen) }
                }
                IconButton(onClick = onSil) { Icon(Icons.Filled.Delete, "Sil", tint = MaterialTheme.colorScheme.error) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AmelEkleSheet(
    sheetState: androidx.compose.material3.SheetState,
    onDismiss: () -> Unit,
    onEkle: (String, String) -> Unit
) {
    var baslik by remember { mutableStateOf("") }
    var tur    by remember { mutableStateOf("İbadet") }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = Modifier.padding(Spacing.md)) {
            Text("Yeni Amel", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(Spacing.md))
            OutlinedTextField(value = baslik, onValueChange = { baslik = it }, label = { Text("Amel Başlığı") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(Spacing.sm))
            OutlinedTextField(value = tur, onValueChange = { tur = it }, label = { Text("Tür (İbadet / Zikir / Sadaka...)") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(Spacing.md))
            TextButton(onClick = { if (baslik.isNotBlank()) onEkle(baslik, tur) }, modifier = Modifier.fillMaxWidth()) {
                Text("Ekle", color = GoldAccent)
            }
            Spacer(Modifier.height(Spacing.xxxl))
        }
    }
}
