package com.kadiri.elkitabi.core.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ZikirAudioManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var soundPool: SoundPool? = null
    private val loadedSounds = mutableMapOf<Int, Int>()
    private var currentSes: ZikirSesi = ZikirSesi.SESSIZ

    enum class ZikirSesi { SESSIZ, TITREŞIM, TEBİH_SESİ, NEY_SESİ, SU_SESİ }

    init {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(3)
            .setAudioAttributes(attrs)
            .build()
    }

    fun setSes(ses: ZikirSesi) {
        currentSes = ses
    }

    fun playZikirTap() {
        when (currentSes) {
            ZikirSesi.TEBİH_SESİ -> playSound(com.kadiri.elkitabi.R.raw.tebih_tap)
            ZikirSesi.NEY_SESİ   -> playSound(com.kadiri.elkitabi.R.raw.ney_tap)
            ZikirSesi.SU_SESİ    -> playSound(com.kadiri.elkitabi.R.raw.su_tap)
            else -> Unit
        }
    }

    fun playTamamSesi() {
        playSound(com.kadiri.elkitabi.R.raw.zikir_tamam)
    }

    private fun playSound(resId: Int) {
        val soundId = loadedSounds.getOrPut(resId) {
            soundPool?.load(context, resId, 1) ?: return
        }
        soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    fun release() {
        soundPool?.release()
        soundPool = null
        loadedSounds.clear()
    }
}
