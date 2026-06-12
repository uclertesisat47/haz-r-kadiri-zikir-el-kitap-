package com.kadiri.elkitabi.features.kitap.domain.repository

import com.kadiri.elkitabi.features.kitap.domain.model.Kitap
import com.kadiri.elkitabi.features.kitap.domain.model.KitapBolum
import com.kadiri.elkitabi.features.kitap.domain.model.YerImi
import kotlinx.coroutines.flow.Flow

interface KitapRepository {
    fun getBolumler(kitapId: String): Flow<List<KitapBolum>>
    suspend fun getBolum(bolumId: Long): KitapBolum?
    suspend fun seedBolumlerIfEmpty(kitapId: String)
    suspend fun markBolumAsRead(bolumId: Long)
    suspend fun getKitapIlerlemesi(kitapId: String): Pair<Int, Int>
    fun getYerImleri(kitapId: String): Flow<List<YerImi>>
    suspend fun addYerImi(yerImi: YerImi): Long
    suspend fun deleteYerImi(yerImiId: Long)
    suspend fun isYerImiExist(kitapId: String, sayfaNo: Int): Boolean
}
