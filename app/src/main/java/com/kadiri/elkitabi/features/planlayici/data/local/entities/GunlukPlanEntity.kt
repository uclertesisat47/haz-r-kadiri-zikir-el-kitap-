package com.kadiri.elkitabi.features.planlayici.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gunluk_plan")
data class GunlukPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tarih: String,
    val ibadetAdi: String,
    val planlananSaat: String,
    val tamamlandi: Boolean = false,
    val ozelMi: Boolean = false
)
