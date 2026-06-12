package com.kadiri.elkitabi.features.kitap.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satir_notlar")
data class SatirNotEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sayfaId: Long,
    val secilenMetin: String,
    val notMetni: String,
    val tarih: String
)
