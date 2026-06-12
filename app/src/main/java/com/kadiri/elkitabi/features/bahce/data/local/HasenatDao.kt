package com.kadiri.elkitabi.features.bahce.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.bahce.data.local.entities.HasenatBitkiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HasenatDao {
    @Query("SELECT * FROM hasenat_bitkiler ORDER BY edinimTarihi DESC")
    fun getAllBitkiler(): Flow<List<HasenatBitkiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBitki(bitki: HasenatBitkiEntity): Long

    @Update
    suspend fun updateBitki(bitki: HasenatBitkiEntity)

    @Delete
    suspend fun deleteBitki(bitki: HasenatBitkiEntity)

    @Query("DELETE FROM hasenat_bitkiler WHERE saglik <= 0")
    suspend fun deleteOlmusBitkiler()

    @Query("UPDATE hasenat_bitkiler SET saglik = :saglik, sulamaTarihi = :tarih WHERE id = :id")
    suspend fun updateSaglik(id: Long, saglik: Int, tarih: String)

    @Query("SELECT SUM(saglik) FROM hasenat_bitkiler")
    fun getTotalHasenat(): Flow<Int?>

    @Query("SELECT COUNT(*) FROM hasenat_bitkiler WHERE saglik > 30")
    fun getSaglikliSayi(): Flow<Int>
}
