package com.kadiri.elkitabi.features.ramazan.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ramazan_gunler")
data class RamazanGunEntity(
    @PrimaryKey
    val tarih: String,
    val orucTutuldu: Boolean = false,
    val teravihKilindi: Boolean = false,
    val teravihRekat: Int = 0,
    val hatimSayfa: Int = 0,
    val zikirSayisi: Int = 0,
    val notlar: String = ""
)
