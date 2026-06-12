package com.kadiri.elkitabi.core.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class GunlukHatirlaticiWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationScheduler: NotificationScheduler
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val tip = inputData.getString("tip") ?: return Result.success()
        when (tip) {
            "SABAH_EZKAR" -> notificationScheduler.bildirimGonder(
                "☀️ Sabah Ezkarı",
                "Sabah ezkarlarınızı okudunuz mu? Günü bereketli başlatın.",
                NotificationScheduler.CHANNEL_GUNLUK,
                1001
            )
            "AKSAM_EZKAR" -> notificationScheduler.bildirimGonder(
                "🌙 Akşam Ezkarı",
                "Akşam ezkarı vakti yaklaşıyor. Unutmayın!",
                NotificationScheduler.CHANNEL_GUNLUK,
                1002
            )
            "ZIKIR" -> notificationScheduler.bildirimGonder(
                "📿 Günlük Zikir",
                "Günlük zikirlerinizi tamamladınız mı? Hasenât bahçeniz sizi bekliyor.",
                NotificationScheduler.CHANNEL_GUNLUK,
                1003
            )
        }
        return Result.success()
    }
}
