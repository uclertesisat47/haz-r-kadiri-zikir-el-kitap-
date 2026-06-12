package com.kadiri.elkitabi.core.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            Log.d("BootReceiver", "Cihaz açıldı, bildirimler yeniden ayarlanıyor.")
            // PrayerAlarmScheduler.reschedule(context) // Namaz vakti alarmlarını yeniden kur
        }
    }
}
