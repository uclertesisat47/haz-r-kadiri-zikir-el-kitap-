package com.kadiri.elkitabi.features.siyer.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "siyer_favoriler")
data class SiyerFavoriEntity(
    @PrimaryKey
    val olayId: Int,
    val eklemeTarihi: String
)
