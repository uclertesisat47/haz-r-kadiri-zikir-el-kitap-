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

class NamazWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            NamazWidgetContent()
        }
    }
}

@Composable
private fun NamazWidgetContent() {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF0F3D2C))
            .clickable(actionStartActivity<MainActivity>())
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "🕌 Namaz Vakitleri",
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color(0xFFD4AF37)), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(GlanceModifier.height(4.dp))
            Text(
                text = "Sonraki: Öğle",
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color.White), fontSize = 12.sp)
            )
            Text(
                text = "Uygulama için dokunun",
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color.White.copy(alpha = 0.6f)), fontSize = 10.sp)
            )
        }
    }
}

class NamazWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = NamazWidget()
}
