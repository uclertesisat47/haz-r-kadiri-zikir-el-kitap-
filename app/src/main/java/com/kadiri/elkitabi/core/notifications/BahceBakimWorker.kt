package com.kadiri.elkitabi.core.notifications

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kadiri.elkitabi.features.bahce.data.local.HasenatDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@HiltWorker
class BahceBakimWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val hasenatDao: HasenatDao,
    private val notificationScheduler: NotificationScheduler
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val bugun = LocalDate.now()
        // Bitki sağlık güncellemesi - tek tek işle
        // In real app, we'd get all bitkiler and update each one
        // This is simplified - actual implementation would use collectLatest
        notificationScheduler.bildirimGonder(
            "🌸 Hasenât Bahçeniz",
            "Bahçeniz bakım bekliyor! İbadetlerinizle bitkilerinizi sulayın.",
            NotificationScheduler.CHANNEL_BAHCE,
            2001
        )
        return Result.success()
    }
}
