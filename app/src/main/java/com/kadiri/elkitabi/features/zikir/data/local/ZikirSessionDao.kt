package com.kadiri.elkitabi.features.zikir.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZikirSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ZikirSessionEntity): Long

    @Query("SELECT * FROM zikir_sessions ORDER BY tarih DESC")
    fun getAllSessions(): Flow<List<ZikirSessionEntity>>

    @Query("SELECT * FROM zikir_sessions WHERE tarih >= :startMillis ORDER BY tarih DESC")
    fun getSessionsFrom(startMillis: Long): Flow<List<ZikirSessionEntity>>

    @Query("SELECT * FROM zikir_sessions WHERE tarih >= :startMillis AND tarih < :endMillis ORDER BY tarih DESC")
    suspend fun getSessionsBetween(startMillis: Long, endMillis: Long): List<ZikirSessionEntity>

    @Query("SELECT SUM(count) FROM zikir_sessions")
    suspend fun getTotalCount(): Long?

    @Query("SELECT SUM(count) FROM zikir_sessions WHERE tarih >= :startMillis")
    suspend fun getCountFrom(startMillis: Long): Int?

    @Query("SELECT SUM(sure) FROM zikir_sessions")
    suspend fun getTotalDuration(): Long?

    @Query("SELECT zikirAdi, SUM(count) as total FROM zikir_sessions GROUP BY zikirAdi ORDER BY total DESC LIMIT 1")
    suspend fun getMostUsedZikir(): EnCokZikirResult?

    @Query("SELECT DISTINCT date(tarih / 1000, 'unixepoch') as gun FROM zikir_sessions ORDER BY gun DESC")
    suspend fun getActiveDays(): List<String>

    @Query("DELETE FROM zikir_sessions WHERE id = :id")
    suspend fun deleteSession(id: Long)

    data class EnCokZikirResult(val zikirAdi: String, val total: Int)
}
