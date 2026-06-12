package com.kadiri.elkitabi.features.zikir.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zikir_hedefler")
data class ZikirHedefEntity(
    @PrimaryKey val zikirId: Int,
    val gunlukHedef: Int = 33,
    val haftalikHedef: Int = 231
)
