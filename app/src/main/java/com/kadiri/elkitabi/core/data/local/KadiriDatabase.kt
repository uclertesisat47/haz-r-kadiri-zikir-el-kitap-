package com.kadiri.elkitabi.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadiri.elkitabi.features.kitap.data.local.KitapDao
import com.kadiri.elkitabi.features.kitap.data.local.entities.KitapBolumEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.KitapSayfaEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.YerImiEntity
import com.kadiri.elkitabi.features.zikir.data.local.ZikirDao
import com.kadiri.elkitabi.features.zikir.data.local.ZikirSessionDao
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirEntity
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirHedefEntity
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirSessionEntity
import com.kadiri.elkitabi.features.amel.data.local.AmelDao
import com.kadiri.elkitabi.features.amel.data.local.entities.AmelEntity

@Database(
    entities = [
        ZikirEntity::class,
        ZikirSessionEntity::class,
        ZikirHedefEntity::class,
        KitapBolumEntity::class,
        KitapSayfaEntity::class,
        YerImiEntity::class,
        AmelEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class KadiriDatabase : RoomDatabase() {
    abstract fun zikirDao(): ZikirDao
    abstract fun zikirSessionDao(): ZikirSessionDao
    abstract fun kitapDao(): KitapDao
    abstract fun amelDao(): AmelDao
}
