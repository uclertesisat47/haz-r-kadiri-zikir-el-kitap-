package com.kadiri.elkitabi.features.zikir.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirEntity
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirHedefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ZikirDao {

    @Query("SELECT * FROM zikirler ORDER BY id ASC")
    fun getAllZikirler(): Flow<List<ZikirEntity>>

    @Query("SELECT * FROM zikirler WHERE id = :id")
    suspend fun getZikirById(id: Int): ZikirEntity?

    @Query("SELECT * FROM zikirler WHERE kategori = :kategori ORDER BY id ASC")
    fun getZikirlerByKategori(kategori: String): Flow<List<ZikirEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZikir(zikir: ZikirEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZikirler(zikirler: List<ZikirEntity>)

    @Update
    suspend fun updateZikir(zikir: ZikirEntity)

    @Query("DELETE FROM zikirler WHERE id = :id")
    suspend fun deleteZikir(id: Int)

    @Query("SELECT COUNT(*) FROM zikirler")
    suspend fun getZikirCount(): Int

    // Hedefler
    @Query("SELECT * FROM zikir_hedefler WHERE zikirId = :zikirId")
    suspend fun getHedef(zikirId: Int): ZikirHedefEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHedef(hedef: ZikirHedefEntity)

    @Query("DELETE FROM zikir_hedefler WHERE zikirId = :zikirId")
    suspend fun deleteHedef(zikirId: Int)
}
