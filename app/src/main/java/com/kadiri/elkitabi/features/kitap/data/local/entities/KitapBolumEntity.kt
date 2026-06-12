package com.kadiri.elkitabi.features.kitap.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kitap_bolumler")
data class KitapBolumEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val kitapId: String,
    val bolumNo: Int,
    val baslik: String,
    val arabicBaslik: String = "",
    val icerik: String,
    val sayfa: Int = 0,
    val okundu: Boolean = false
)

@Entity(tableName = "kitap_sayfalar")
data class KitapSayfaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bolumId: Long,
    val kitapId: String,
    val sayfaNo: Int,
    val arabicIcerik: String = "",
    val turkceMeali: String = "",
    val aciklama: String = ""
)

@Entity(tableName = "yer_imleri")
data class YerImiEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val kitapId: String,
    val bolumId: Long,
    val sayfaNo: Int,
    val not: String = "",
    val tarih: Long = System.currentTimeMillis()
)
