package com.kadiri.elkitabi.features.gunluk.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.gunluk.data.local.entities.GunlukEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GunlukDao {
    @Query("SELECT * FROM manevi_gunluk ORDER BY tarih DESC")
    fun getAllEntries(): Flow<List<GunlukEntryEntity>>

    @Query("SELECT * FROM manevi_gunluk WHERE tarih = :tarih LIMIT 1")
    suspend fun getEntryByTarih(tarih: String): GunlukEntryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: GunlukEntryEntity): Long

    @Update
    suspend fun updateEntry(entry: GunlukEntryEntity)

    @Delete
    suspend fun deleteEntry(entry: GunlukEntryEntity)

    @Query("SELECT COUNT(*) FROM manevi_gunluk")
    fun getTotalGunSayisi(): Flow<Int>

    @Query("SELECT * FROM manevi_gunluk ORDER BY tarih DESC LIMIT 7")
    fun getSonYediGun(): Flow<List<GunlukEntryEntity>>

    @Query("SELECT * FROM manevi_gunluk WHERE tarih BETWEEN :baslangic AND :bitis ORDER BY tarih ASC")
    fun getEntriesBetweenDates(baslangic: String, bitis: String): Flow<List<GunlukEntryEntity>>
}
