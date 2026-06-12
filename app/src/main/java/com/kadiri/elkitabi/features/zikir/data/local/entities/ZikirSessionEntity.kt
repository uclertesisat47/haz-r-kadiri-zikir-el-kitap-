package com.kadiri.elkitabi.features.zikir.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zikir_sessions")
data class ZikirSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val zikirId: Int,
    val zikirAdi: String,
    val count: Int,
    val hedef: Int,
    val tarih: Long,
    val sure: Long,
    val tamamlandi: Boolean,
    val notlar: String = ""
)
