package com.kadiri.elkitabi.features.ramazan.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.ramazan.data.local.entities.RamazanGunEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RamazanDao {
    @Query("SELECT * FROM ramazan_gunler ORDER BY tarih ASC")
    fun getAllGunler(): Flow<List<RamazanGunEntity>>

    @Query("SELECT * FROM ramazan_gunler WHERE tarih = :tarih LIMIT 1")
    suspend fun getGunByTarih(tarih: String): RamazanGunEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(gun: RamazanGunEntity)

    @Query("SELECT COUNT(*) FROM ramazan_gunler WHERE orucTutuldu = 1")
    fun getTutulanOrucSayisi(): Flow<Int>

    @Query("SELECT COUNT(*) FROM ramazan_gunler WHERE teravihKilindi = 1")
    fun getKilinanTeravihSayisi(): Flow<Int>

    @Query("SELECT SUM(hatimSayfa) FROM ramazan_gunler")
    fun getTotalHatimSayfa(): Flow<Int?>
}
