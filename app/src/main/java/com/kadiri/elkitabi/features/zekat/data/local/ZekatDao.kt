package com.kadiri.elkitabi.features.zekat.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.zekat.data.local.entities.ZekatHesapEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZekatDao {
    @Query("SELECT * FROM zekat_hesaplar ORDER BY tarih DESC")
    fun getAllHesaplar(): Flow<List<ZekatHesapEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHesap(hesap: ZekatHesapEntity): Long

    @Delete
    suspend fun deleteHesap(hesap: ZekatHesapEntity)

    @Query("SELECT * FROM zekat_hesaplar ORDER BY tarih DESC LIMIT 1")
    suspend fun getSonHesap(): ZekatHesapEntity?
}
