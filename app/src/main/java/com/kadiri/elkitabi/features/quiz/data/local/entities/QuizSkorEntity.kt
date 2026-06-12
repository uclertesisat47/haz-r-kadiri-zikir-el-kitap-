package com.kadiri.elkitabi.features.quiz.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_skorlar")
data class QuizSkorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val kategori: String,
    val dogru: Int,
    val yanlis: Int,
    val toplamSure: Long,
    val tarih: String
)
