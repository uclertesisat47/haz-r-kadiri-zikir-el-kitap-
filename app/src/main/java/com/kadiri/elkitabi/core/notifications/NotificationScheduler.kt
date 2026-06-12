package com.kadiri.elkitabi.core.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.kadiri.elkitabi.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        const val CHANNEL_NAMAZ = "namaz_channel"
        const val CHANNEL_GUNLUK = "gunluk_channel"
        const val CHANNEL_BAHCE = "bahce_channel"
        const val CHANNEL_RAMAZAN = "ramazan_channel"
    }

    fun kanallarOlustur() {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        listOf(
            NotificationChannel(CHANNEL_NAMAZ, "Namaz Bildirimleri", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Namaz vakti bildirimleri"
            },
            NotificationChannel(CHANNEL_GUNLUK, "Günlük Hatırlatıcılar", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Zikir ve amel hatırlatıcıları"
            },
            NotificationChannel(CHANNEL_BAHCE, "Hasenât Bahçesi", NotificationManager.IMPORTANCE_LOW).apply {
                description = "Bahçe durumu bildirimleri"
            },
            NotificationChannel(CHANNEL_RAMAZAN, "Ramazan Bildirimleri", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "İmsak ve iftar bildirimleri"
            }
        ).forEach { nm.createNotificationChannel(it) }
    }

    fun gunlukHatirlaticilarZamanla() {
        val work = WorkManager.getInstance(context)
        val sabahEzkarWork = PeriodicWorkRequestBuilder<GunlukHatirlaticiWorker>(1, TimeUnit.DAYS)
            .setInputData(workDataOf("tip" to "SABAH_EZKAR", "saat" to 9, "dakika" to 0))
            .addTag("sabah_ezkar")
            .build()
        work.enqueueUniquePeriodicWork("sabah_ezkar", ExistingPeriodicWorkPolicy.UPDATE, sabahEzkarWork)

        val aksamEzkarWork = PeriodicWorkRequestBuilder<GunlukHatirlaticiWorker>(1, TimeUnit.DAYS)
            .setInputData(workDataOf("tip" to "AKSAM_EZKAR", "saat" to 17, "dakika" to 0))
            .addTag("aksam_ezkar")
            .build()
        work.enqueueUniquePeriodicWork("aksam_ezkar", ExistingPeriodicWorkPolicy.UPDATE, aksamEzkarWork)

        val zikirWork = PeriodicWorkRequestBuilder<GunlukHatirlaticiWorker>(1, TimeUnit.DAYS)
            .setInputData(workDataOf("tip" to "ZIKIR", "saat" to 21, "dakika" to 0))
            .addTag("zikir_hatir")
            .build()
        work.enqueueUniquePeriodicWork("zikir_hatir", ExistingPeriodicWorkPolicy.UPDATE, zikirWork)
    }

    fun bahceBakimZamanla() {
        val bahceWork = PeriodicWorkRequestBuilder<BahceBakimWorker>(24, TimeUnit.HOURS)
            .addTag("bahce_bakim")
            .build()
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork("bahce_bakim", ExistingPeriodicWorkPolicy.KEEP, bahceWork)
    }

    fun bildirimGonder(baslik: String, mesaj: String, kanal: String, id: Int = System.currentTimeMillis().toInt()) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pi = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_IMMUTABLE)
        val notif = NotificationCompat.Builder(context, kanal)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(baslik)
            .setContentText(mesaj)
            .setStyle(NotificationCompat.BigTextStyle().bigText(mesaj))
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()
        nm.notify(id, notif)
    }
}
