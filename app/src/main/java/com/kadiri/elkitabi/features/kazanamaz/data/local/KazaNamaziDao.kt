package com.kadiri.elkitabi.features.kazanamaz.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.kazanamaz.data.local.entities.KazaNamaziEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KazaNamaziDao {
    @Query("SELECT * FROM kaza_namazlari ORDER BY olusturmaTarihi DESC LIMIT 1")
    fun getAktifKaza(): Flow<KazaNamaziEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(kaza: KazaNamaziEntity): Long

    @Query("UPDATE kaza_namazlari SET tamamlananSayisi = tamamlananSayisi + :miktar WHERE id = :id")
    suspend fun addTamamlanan(id: Long, miktar: Int)
}
