package com.kadiri.elkitabi.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadiri.elkitabi.features.amel.data.local.AmelDao
import com.kadiri.elkitabi.features.amel.data.local.entities.AmelEntity
import com.kadiri.elkitabi.features.bahce.data.local.HasenatDao
import com.kadiri.elkitabi.features.bahce.data.local.entities.HasenatBitkiEntity
import com.kadiri.elkitabi.features.gunluk.data.local.GunlukDao
import com.kadiri.elkitabi.features.gunluk.data.local.entities.GunlukEntryEntity
import com.kadiri.elkitabi.features.kazanamaz.data.local.KazaNamaziDao
import com.kadiri.elkitabi.features.kazanamaz.data.local.entities.KazaNamaziEntity
import com.kadiri.elkitabi.features.kitap.data.local.KitapDao
import com.kadiri.elkitabi.features.kitap.data.local.VurguNotDao
import com.kadiri.elkitabi.features.kitap.data.local.entities.KitapBolumEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.KitapSayfaEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.SatirNotEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.SatirVurguEntity
import com.kadiri.elkitabi.features.kitap.data.local.entities.YerImiEntity
import com.kadiri.elkitabi.features.planlayici.data.local.GunlukPlanDao
import com.kadiri.elkitabi.features.planlayici.data.local.entities.GunlukPlanEntity
import com.kadiri.elkitabi.features.quiz.data.local.QuizDao
import com.kadiri.elkitabi.features.quiz.data.local.entities.QuizSkorEntity
import com.kadiri.elkitabi.features.ramazan.data.local.RamazanDao
import com.kadiri.elkitabi.features.ramazan.data.local.entities.RamazanGunEntity
import com.kadiri.elkitabi.features.siyer.data.local.SiyerDao
import com.kadiri.elkitabi.features.siyer.data.local.entities.SiyerFavoriEntity
import com.kadiri.elkitabi.features.zekat.data.local.ZekatDao
import com.kadiri.elkitabi.features.zekat.data.local.entities.ZekatHesapEntity
import com.kadiri.elkitabi.features.zikir.data.local.ZikirDao
import com.kadiri.elkitabi.features.zikir.data.local.ZikirSessionDao
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirEntity
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirHedefEntity
import com.kadiri.elkitabi.features.zikir.data.local.entities.ZikirSessionEntity

@Database(
    entities = [
        ZikirEntity::class,
        ZikirSessionEntity::class,
        ZikirHedefEntity::class,
        KitapBolumEntity::class,
        KitapSayfaEntity::class,
        YerImiEntity::class,
        AmelEntity::class,
        HasenatBitkiEntity::class,
        GunlukEntryEntity::class,
        QuizSkorEntity::class,
        RamazanGunEntity::class,
        KazaNamaziEntity::class,
        GunlukPlanEntity::class,
        ZekatHesapEntity::class,
        SiyerFavoriEntity::class,
        SatirVurguEntity::class,
        SatirNotEntity::class
    ],
    version = 2,
    exportSchema = true
)
abstract class KadiriDatabase : RoomDatabase() {
    abstract fun zikirDao(): ZikirDao
    abstract fun zikirSessionDao(): ZikirSessionDao
    abstract fun kitapDao(): KitapDao
    abstract fun amelDao(): AmelDao
    abstract fun hasenatDao(): HasenatDao
    abstract fun gunlukDao(): GunlukDao
    abstract fun quizDao(): QuizDao
    abstract fun ramazanDao(): RamazanDao
    abstract fun kazaNamaziDao(): KazaNamaziDao
    abstract fun gunlukPlanDao(): GunlukPlanDao
    abstract fun zekatDao(): ZekatDao
    abstract fun siyerDao(): SiyerDao
    abstract fun vurguNotDao(): VurguNotDao
}
