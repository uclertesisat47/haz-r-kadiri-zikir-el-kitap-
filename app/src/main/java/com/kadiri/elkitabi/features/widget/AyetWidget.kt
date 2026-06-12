package com.kadiri.elkitabi.features.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.kadiri.elkitabi.MainActivity
import java.time.LocalDate

class AyetWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { AyetWidgetContent() }
    }
}

@Composable
private fun AyetWidgetContent() {
    val ayetler = listOf(
        "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا" to "Zorlukla birlikte kolaylık vardır. (İnşirah 5)",
        "وَاللَّهُ خَيْرُ الرَّازِقِينَ" to "Allah rızık verenlerin en hayırlısıdır. (Cuma 11)",
        "إِنَّ اللَّهَ مَعَ الصَّابِرِينَ" to "Allah sabredenlerle beraberdir. (Bakara 153)"
    )
    val idx = LocalDate.now().dayOfYear % ayetler.size
    val (arapca, meal) = ayetler[idx]

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF1B4D3E))
            .clickable(actionStartActivity<MainActivity>())
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "✨ Günün Ayeti",
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color(0xFFD4AF37)), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(GlanceModifier.height(8.dp))
            Text(
                text = arapca,
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color(0xFFD4AF37)), fontSize = 16.sp)
            )
            Spacer(GlanceModifier.height(4.dp))
            Text(
                text = meal,
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color.White.copy(alpha = 0.8f)), fontSize = 10.sp)
            )
        }
    }
}

class AyetWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = AyetWidget()
}
