package com.kadiri.elkitabi.features.amel.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kadiri.elkitabi.features.amel.data.local.entities.AmelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AmelDao {

    @Query("SELECT * FROM amel_defteri ORDER BY tarih DESC")
    fun getAllAmeller(): Flow<List<AmelEntity>>

    @Query("SELECT * FROM amel_defteri WHERE tarih >= :startOfDay ORDER BY tarih DESC")
    fun getBugunAmeller(startOfDay: Long): Flow<List<AmelEntity>>

    @Query("SELECT * FROM amel_defteri WHERE tarih BETWEEN :start AND :end ORDER BY tarih DESC")
    fun getAmellerByDateRange(start: Long, end: Long): Flow<List<AmelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAmel(amel: AmelEntity): Long

    @Update
    suspend fun updateAmel(amel: AmelEntity)

    @Query("DELETE FROM amel_defteri WHERE id = :amelId")
    suspend fun deleteAmel(amelId: Long)

    @Query("SELECT COUNT(*) FROM amel_defteri WHERE tarih >= :startOfDay AND tamamlandi = 1")
    suspend fun getTamamlananBugunSayisi(startOfDay: Long): Int
}
