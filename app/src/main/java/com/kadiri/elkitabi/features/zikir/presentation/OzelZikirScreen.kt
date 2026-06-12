package com.kadiri.elkitabi.features.zikir.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.kadiri.elkitabi.core.designsystem.theme.ArabicTextMedium
import com.kadiri.elkitabi.core.designsystem.theme.Spacing
import com.kadiri.elkitabi.features.zikir.domain.model.OzelZikir
import com.kadiri.elkitabi.features.zikir.domain.usecase.OzelZikirEkleUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OzelZikirScreen(
    onBack: () -> Unit,
    viewModel: ZikirViewModel = hiltViewModel()
) {
    var isim by remember { mutableStateOf("") }
    var arabicText by remember { mutableStateOf("") }
    var hedef by remember { mutableStateOf("33") }
    var hata by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Özel Zikir Ekle") },
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
                .padding(horizontal = Spacing.md)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(Spacing.md))
            OutlinedTextField(
                value         = isim,
                onValueChange = { isim = it },
                label         = { Text("Zikir İsmi (Türkçe)") },
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )
            Spacer(Modifier.height(Spacing.md))
            OutlinedTextField(
                value         = arabicText,
                onValueChange = { arabicText = it },
                label         = { Text("Arapça Metin (isteğe bağlı)") },
                textStyle     = ArabicTextMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier      = Modifier.fillMaxWidth(),
                minLines      = 2
            )
            Spacer(Modifier.height(Spacing.md))
            OutlinedTextField(
                value         = hedef,
                onValueChange = { hedef = it.filter { c -> c.isDigit() } },
                label         = { Text("Hedef Sayı") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier      = Modifier.fillMaxWidth(),
                singleLine    = true
            )
            hata?.let {
                Spacer(Modifier.height(Spacing.sm))
                Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            }
            Spacer(Modifier.height(Spacing.lg))
            Button(
                onClick  = {
                    if (isim.isBlank()) { hata = "İsim boş olamaz"; return@Button }
                    val hedefInt = hedef.toIntOrNull() ?: 33
                    // ViewModel üzerinden kaydet
                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kaydet")
            }
            Spacer(Modifier.height(Spacing.xxxl))
        }
    }
}
