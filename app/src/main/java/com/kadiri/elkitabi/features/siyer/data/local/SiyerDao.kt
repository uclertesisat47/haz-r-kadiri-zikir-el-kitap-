package com.kadiri.elkitabi.features.siyer.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.siyer.data.local.entities.SiyerFavoriEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SiyerDao {
    @Query("SELECT * FROM siyer_favoriler ORDER BY eklemeTarihi DESC")
    fun getAllFavoriler(): Flow<List<SiyerFavoriEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavori(favori: SiyerFavoriEntity)

    @Delete
    suspend fun removeFavori(favori: SiyerFavoriEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM siyer_favoriler WHERE olayId = :olayId)")
    suspend fun isFavori(olayId: Int): Boolean
}
