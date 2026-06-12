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

class ZikirWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            ZikirWidgetContent()
        }
    }
}

@Composable
private fun ZikirWidgetContent() {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color(0xFF080A09))
            .clickable(actionStartActivity<MainActivity>())
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "📿",
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color.White), fontSize = 28.sp)
            )
            Spacer(GlanceModifier.height(4.dp))
            Text(
                text = "Zikirmatik",
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color(0xFFD4AF37)), fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Bugün: — zikir",
                style = TextStyle(color = androidx.glance.unit.ColorProvider(Color.White), fontSize = 11.sp)
            )
        }
    }
}

class ZikirWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget = ZikirWidget()
}
