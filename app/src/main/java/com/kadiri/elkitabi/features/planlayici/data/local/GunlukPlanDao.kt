package com.kadiri.elkitabi.features.planlayici.data.local

import androidx.room.*
import com.kadiri.elkitabi.features.planlayici.data.local.entities.GunlukPlanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GunlukPlanDao {
    @Query("SELECT * FROM gunluk_plan WHERE tarih = :tarih ORDER BY planlananSaat ASC")
    fun getPlanByTarih(tarih: String): Flow<List<GunlukPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: GunlukPlanEntity): Long

    @Update
    suspend fun updatePlan(plan: GunlukPlanEntity)

    @Delete
    suspend fun deletePlan(plan: GunlukPlanEntity)

    @Query("UPDATE gunluk_plan SET tamamlandi = :tamamlandi WHERE id = :id")
    suspend fun setTamamlandi(id: Long, tamamlandi: Boolean)

    @Query("SELECT COUNT(*) FROM gunluk_plan WHERE tarih = :tarih AND tamamlandi = 1")
    fun getTamamlananSayisi(tarih: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM gunluk_plan WHERE tarih = :tarih")
    fun getTotalSayisi(tarih: String): Flow<Int>
}
