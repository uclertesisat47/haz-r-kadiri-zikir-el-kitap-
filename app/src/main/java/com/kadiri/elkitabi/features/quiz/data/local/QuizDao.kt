package com.kadiri.elkitabi.features.quiz.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.quiz.data.local.entities.QuizSkorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz_skorlar ORDER BY tarih DESC")
    fun getAllSkorlar(): Flow<List<QuizSkorEntity>>

    @Query("SELECT * FROM quiz_skorlar WHERE kategori = :kategori ORDER BY dogru DESC LIMIT 10")
    fun getSkorlarByKategori(kategori: String): Flow<List<QuizSkorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkor(skor: QuizSkorEntity): Long

    @Query("SELECT MAX(dogru) FROM quiz_skorlar WHERE kategori = :kategori")
    fun getEnYuksekSkor(kategori: String): Flow<Int?>

    @Query("SELECT COUNT(*) FROM quiz_skorlar WHERE tarih = :bugun")
    suspend fun getBugunQuizSayisi(bugun: String): Int
}
