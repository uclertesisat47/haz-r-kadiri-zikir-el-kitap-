package com.kadiri.elkitabi.features.amel.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amel_defteri")
data class AmelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tarih: Long = System.currentTimeMillis(),
    val amelTuru: String,
    val baslik: String,
    val aciklama: String = "",
    val miktar: Int = 1,
    val birim: String = "kez",
    val tamamlandi: Boolean = false,
    val not: String = ""
)
