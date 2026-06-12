package com.kadiri.elkitabi.features.kitap.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satir_vurgular")
data class SatirVurguEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sayfaId: Long,
    val baslangicOffset: Int,
    val bitisOffset: Int,
    val renk: String = "YELLOW",
    val tarih: String
)
