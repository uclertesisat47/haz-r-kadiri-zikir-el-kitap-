package com.kadiri.elkitabi.features.zekat.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zekat_hesaplar")
data class ZekatHesapEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tarih: String,
    val altinGram: Double = 0.0,
    val gumuzGram: Double = 0.0,
    val nakit: Double = 0.0,
    val ticaret: Double = 0.0,
    val hisseler: Double = 0.0,
    val kiraGeliri: Double = 0.0,
    val borclar: Double = 0.0,
    val zekatMiktari: Double = 0.0,
    val yukumluMu: Boolean = false
)
