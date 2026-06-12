package com.kadiri.elkitabi.features.zikir.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zikirler")
data class ZikirEntity(
    @PrimaryKey val id: Int,
    val arabicName: String,
    val turkishName: String,
    val arabicText: String,
    val turkishMeaning: String,
    val hedefSayilar: String,   // JSON list: "33,99,100"
    val varsayilanHedef: Int,
    val fazileti: String,
    val kategori: String,
    val sesResId: Int? = null
)

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

@Entity(tableName = "zikir_hedefler")
data class ZikirHedefEntity(
    @PrimaryKey val zikirId: Int,
    val gunlukHedef: Int,
    val haftalikHedef: Int
)
