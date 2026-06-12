package com.kadiri.elkitabi.features.sharing

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kadiri.elkitabi.core.designsystem.components.GlassCard
import com.kadiri.elkitabi.core.designsystem.theme.*

data class PaylasimKarti(
    val id: Int,
    val tip: String,
    val arapca: String,
    val turkce: String,
    val kaynak: String,
    val arkaplanRenk: List<Color>
)

private val kartlar = listOf(
    PaylasimKarti(
        1, "Ayet",
        "وَمَن يَتَّقِ اللَّهَ يَجْعَل لَّهُ مَخْرَجًا",
        "Kim Allah'a karşı gelmekten sakınırsa, Allah ona bir çıkış yolu açar.",
        "Talak 2-3",
        listOf(Color(0xFF0F3D2C), Color(0xFF1B4D3E))
    ),
    PaylasimKarti(
        2, "Hadis",
        "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ",
        "Ameller niyetlere göredir.",
        "Buhâri, Müslim",
        listOf(Color(0xFF3A2800), Color(0xFF5C4200))
    ),
    PaylasimKarti(
        3, "Ayet",
        "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا",
        "Şüphesiz zorlukla birlikte kolaylık vardır.",
        "İnşirah 5",
        listOf(Color(0xFF3D0060), Color(0xFF560087))
    ),
    PaylasimKarti(
        4, "Dua",
        "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الآخِرَةِ حَسَنَةً",
        "Rabbimiz! Bize dünyada da iyilik ver, ahirette de iyilik ver.",
        "Bakara 201",
        listOf(Color(0xFF1B4D3E), Color(0xFF0D2C1F))
    ),
    PaylasimKarti(
        5, "Zikir",
        "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
        "Allah'ı tesbih eder ve O'na hamdederim.",
        "Günde 100 kez",
        listOf(Color(0xFF001F3F), Color(0xFF003D7A))
    ),
    PaylasimKarti(
        6, "Hadis",
        "خَيْرُكُمْ مَنْ تَعَلَّمَ الْقُرْآنَ وَعَلَّمَهُ",
        "Sizin en hayırlınız Kuran'ı öğrenen ve öğretendir.",
        "Buhâri",
        listOf(Color(0xFF2D1B00), Color(0xFF5C3900))
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaylasimKartlariScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var secilenKarti by remember { mutableStateOf<PaylasimKarti?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎨 Paylaşım Kartları") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (secilenKarti != null) secilenKarti = null else onBack()
                    }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null) }
                }
            )
        }
    ) { padding ->
        if (secilenKarti != null) {
            val kart = secilenKarti!!
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(Brush.verticalGradient(kart.arkaplanRenk), MaterialTheme.shapes.large),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "بِسْمِ اللَّهِ",
                            style = MaterialTheme.typography.labelLarge,
                            color = GoldAccent.copy(alpha = 0.6f)
                        )
                        Text(
                            kart.arapca,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                textDirection = TextDirection.Rtl,
                                fontSize = 22.sp,
                                lineHeight = 36.sp
                            ),
                            textAlign = TextAlign.Center,
                            color = GoldAccent
                        )
                        HorizontalDivider(color = GoldAccent.copy(alpha = 0.3f))
                        Text(
                            kart.turkce,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            "— ${kart.kaynak}",
                            style = MaterialTheme.typography.labelSmall,
                            color = GoldAccent.copy(alpha = 0.7f)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "📿 Kadiri Tarikatı El Kitabı",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.4f)
                        )
                    }
                }

                Button(
                    onClick = {
                        val shareText = "${kart.arapca}\n\n${kart.turkce}\n\n— ${kart.kaynak}\n\n📿 Kadiri Tarikatı El Kitabı"
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(Intent.createChooser(intent, "Paylaş"))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Share, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Metni Paylaş")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "Bir kart seçin ve paylaşın",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(kartlar) { kart ->
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { secilenKarti = kart }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Surface(
                                    color = GoldAccent.copy(alpha = 0.2f),
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        kart.tip,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = GoldAccent
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    kart.turkce,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 2,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    "— ${kart.kaynak}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Icon(Icons.Filled.Share, null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                        }
                    }
                }
                item { Spacer(Modifier.height(32.dp)) }
            }
        }
    }
}
