package com.kadiri.elkitabi.features.kitap.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.kitap.data.local.entities.SatirNotEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.SatirVurguEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VurguNotDao {
    @Query("SELECT * FROM satir_vurgular WHERE sayfaId = :sayfaId")
    fun getVurgularBySayfa(sayfaId: Long): Flow<List<SatirVurguEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVurgu(vurgu: SatirVurguEntity): Long

    @Delete
    suspend fun deleteVurgu(vurgu: SatirVurguEntity)

    @Query("SELECT * FROM satir_notlar WHERE sayfaId = :sayfaId")
    fun getNotlarBySayfa(sayfaId: Long): Flow<List<SatirNotEntity>>

    @Query("SELECT * FROM satir_notlar ORDER BY tarih DESC")
    fun getAllNotlar(): Flow<List<SatirNotEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNot(not: SatirNotEntity): Long

    @Delete
    suspend fun deleteNot(not: SatirNotEntity)
}
