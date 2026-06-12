package com.kadiri.elkitabi.core.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HapticManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    fun lightImpact() {
        vibrate(30L, VibrationEffect.DEFAULT_AMPLITUDE)
    }

    fun mediumImpact() {
        vibrate(60L, 128)
    }

    fun heavyImpact() {
        vibrate(100L, VibrationEffect.DEFAULT_AMPLITUDE)
    }

    fun zikirTap() {
        vibrate(20L, 80)
    }

    fun successPattern() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings    = longArrayOf(0, 50, 100, 50, 100, 80)
            val amplitudes = intArrayOf(0, 100, 0, 100, 0, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
        }
    }

    fun cancel() {
        vibrator?.cancel()
    }

    private fun vibrate(durationMs: Long, amplitude: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, amplitude))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(durationMs)
        }
    }
}
