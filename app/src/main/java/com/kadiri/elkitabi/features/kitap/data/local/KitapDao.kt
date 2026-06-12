package com.kadiri.elkitabi.features.kitap.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kadiri.elkitabi.features.kitap.data.local.entities.KitapBolumEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.KitapSayfaEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.YerImiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KitapDao {

    // Bölümler
    @Query("SELECT * FROM kitap_bolumler WHERE kitapId = :kitapId ORDER BY bolumNo ASC")
    fun getBolumler(kitapId: String): Flow<List<KitapBolumEntity>>

    @Query("SELECT * FROM kitap_bolumler WHERE id = :bolumId")
    suspend fun getBolum(bolumId: Long): KitapBolumEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBolumler(bolumler: List<KitapBolumEntity>)

    @Update
    suspend fun updateBolum(bolum: KitapBolumEntity)

    @Query("UPDATE kitap_bolumler SET okundu = 1 WHERE id = :bolumId")
    suspend fun markAsRead(bolumId: Long)

    @Query("SELECT COUNT(*) FROM kitap_bolumler WHERE kitapId = :kitapId AND okundu = 1")
    suspend fun getOkunanBolumSayisi(kitapId: String): Int

    @Query("SELECT COUNT(*) FROM kitap_bolumler WHERE kitapId = :kitapId")
    suspend fun getToplamBolumSayisi(kitapId: String): Int

    // Sayfalar
    @Query("SELECT * FROM kitap_sayfalar WHERE bolumId = :bolumId ORDER BY sayfaNo ASC")
    fun getSayfalar(bolumId: Long): Flow<List<KitapSayfaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSayfalar(sayfalar: List<KitapSayfaEntity>)

    // Yer imleri
    @Query("SELECT * FROM yer_imleri WHERE kitapId = :kitapId ORDER BY tarih DESC")
    fun getYerImleri(kitapId: String): Flow<List<YerImiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYerImi(yerImi: YerImiEntity): Long

    @Query("DELETE FROM yer_imleri WHERE id = :yerImiId")
    suspend fun deleteYerImi(yerImiId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM yer_imleri WHERE kitapId = :kitapId AND sayfaNo = :sayfaNo)")
    suspend fun isYerImiExist(kitapId: String, sayfaNo: Int): Boolean
}
