package com.kadiri.elkitabi.features.gunluk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manevi_gunluk")
data class GunlukEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tarih: String,
    val ruhHali: String,
    val ruhHaliPuani: Int = 5,
    val sukurNotu: String = "",
    val tefekkurNotu: String = "",
    val niyetler: String = "",
    val gunAyeti: String = "",
    val gunZikirSayisi: Int = 0,
    val namazDurumu: String = "false,false,false,false,false"
)
