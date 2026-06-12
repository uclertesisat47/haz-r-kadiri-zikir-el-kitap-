package com.kadiri.elkitabi.features.bahce.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "hasenat_bitkiler")
data class HasenatBitkiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tur: String,
    val edinimTarihi: String,
    val sulamaTarihi: String,
    val saglik: Int = 100,
    val seviye: Int = 1,
    val pozisyonX: Float = 0f,
    val pozisyonY: Float = 0f
)
