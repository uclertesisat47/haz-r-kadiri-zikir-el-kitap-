package com.kadiri.elkitabi.features.zikir.domain.repository

import com.kadiri.elkitabi.features.zikir.domain.model.GunlukZikir
import com.kadiri.elkitabi.features.zikir.domain.model.OzelZikir
import com.kadiri.elkitabi.features.zikir.domain.model.Zikir
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirHedef
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirIstatistik
import com.kadiri.elkitabi.features.zikir.domain.model.ZikirSession
import kotlinx.coroutines.flow.Flow

interface ZikirRepository {
    fun getAllZikirler(): Flow<List<Zikir>>
    suspend fun getZikirById(id: Int): Zikir?
    suspend fun saveSession(session: ZikirSession): Long
    fun getAllSessions(): Flow<List<ZikirSession>>
    suspend fun getIstatistik(): ZikirIstatistik
    suspend fun getHedef(zikirId: Int): ZikirHedef?
    suspend fun setHedef(hedef: ZikirHedef)
    suspend fun ekleOzelZikir(ozel: OzelZikir): Long
    suspend fun seedZikirlerIfEmpty()
}
