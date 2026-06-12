package com.kadiri.elkitabi.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kadiri.elkitabi.core.data.local.KadiriDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `hasenat_bitkiler` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tur` TEXT NOT NULL, `edinimTarihi` TEXT NOT NULL, `sulamaTarihi` TEXT NOT NULL, `saglik` INTEGER NOT NULL DEFAULT 100, `seviye` INTEGER NOT NULL DEFAULT 1, `pozisyonX` REAL NOT NULL DEFAULT 0, `pozisyonY` REAL NOT NULL DEFAULT 0)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `manevi_gunluk` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tarih` TEXT NOT NULL, `ruhHali` TEXT NOT NULL, `ruhHaliPuani` INTEGER NOT NULL DEFAULT 5, `sukurNotu` TEXT NOT NULL DEFAULT '', `tefekkurNotu` TEXT NOT NULL DEFAULT '', `niyetler` TEXT NOT NULL DEFAULT '', `gunAyeti` TEXT NOT NULL DEFAULT '', `gunZikirSayisi` INTEGER NOT NULL DEFAULT 0, `namazDurumu` TEXT NOT NULL DEFAULT 'false,false,false,false,false')")
        db.execSQL("CREATE TABLE IF NOT EXISTS `quiz_skorlar` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `kategori` TEXT NOT NULL, `dogru` INTEGER NOT NULL, `yanlis` INTEGER NOT NULL, `toplamSure` INTEGER NOT NULL, `tarih` TEXT NOT NULL)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `ramazan_gunler` (`tarih` TEXT PRIMARY KEY NOT NULL, `orucTutuldu` INTEGER NOT NULL DEFAULT 0, `teravihKilindi` INTEGER NOT NULL DEFAULT 0, `teravihRekat` INTEGER NOT NULL DEFAULT 0, `hatimSayfa` INTEGER NOT NULL DEFAULT 0, `zikirSayisi` INTEGER NOT NULL DEFAULT 0, `notlar` TEXT NOT NULL DEFAULT '')")
        db.execSQL("CREATE TABLE IF NOT EXISTS `kaza_namazlari` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `baslangicYili` INTEGER NOT NULL, `toplamKazaSayisi` INTEGER NOT NULL, `tamamlananSayisi` INTEGER NOT NULL DEFAULT 0, `gunlukHedef` INTEGER NOT NULL DEFAULT 1, `olusturmaTarihi` TEXT NOT NULL)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `gunluk_plan` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tarih` TEXT NOT NULL, `ibadetAdi` TEXT NOT NULL, `planlananSaat` TEXT NOT NULL, `tamamlandi` INTEGER NOT NULL DEFAULT 0, `ozelMi` INTEGER NOT NULL DEFAULT 0)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `zekat_hesaplar` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tarih` TEXT NOT NULL, `altinGram` REAL NOT NULL DEFAULT 0, `gumuzGram` REAL NOT NULL DEFAULT 0, `nakit` REAL NOT NULL DEFAULT 0, `ticaret` REAL NOT NULL DEFAULT 0, `hisseler` REAL NOT NULL DEFAULT 0, `kiraGeliri` REAL NOT NULL DEFAULT 0, `borclar` REAL NOT NULL DEFAULT 0, `zekatMiktari` REAL NOT NULL DEFAULT 0, `yukumluMu` INTEGER NOT NULL DEFAULT 0)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `siyer_favoriler` (`olayId` INTEGER PRIMARY KEY NOT NULL, `eklemeTarihi` TEXT NOT NULL)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `satir_vurgular` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sayfaId` INTEGER NOT NULL, `baslangicOffset` INTEGER NOT NULL, `bitisOffset` INTEGER NOT NULL, `renk` TEXT NOT NULL DEFAULT 'YELLOW', `tarih` TEXT NOT NULL)")
        db.execSQL("CREATE TABLE IF NOT EXISTS `satir_notlar` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sayfaId` INTEGER NOT NULL, `secilenMetin` TEXT NOT NULL, `notMetni` TEXT NOT NULL, `tarih` TEXT NOT NULL)")
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KadiriDatabase =
        Room.databaseBuilder(context, KadiriDatabase::class.java, "kadiri_database.db")
            .addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides fun provideZikirDao(db: KadiriDatabase)        = db.zikirDao()
    @Provides fun provideZikirSessionDao(db: KadiriDatabase) = db.zikirSessionDao()
    @Provides fun provideKitapDao(db: KadiriDatabase)        = db.kitapDao()
    @Provides fun provideAmelDao(db: KadiriDatabase)         = db.amelDao()
    @Provides fun provideHasenatDao(db: KadiriDatabase)      = db.hasenatDao()
    @Provides fun provideGunlukDao(db: KadiriDatabase)       = db.gunlukDao()
    @Provides fun provideQuizDao(db: KadiriDatabase)         = db.quizDao()
    @Provides fun provideRamazanDao(db: KadiriDatabase)      = db.ramazanDao()
    @Provides fun provideKazaNamaziDao(db: KadiriDatabase)   = db.kazaNamaziDao()
    @Provides fun provideGunlukPlanDao(db: KadiriDatabase)   = db.gunlukPlanDao()
    @Provides fun provideZekatDao(db: KadiriDatabase)        = db.zekatDao()
    @Provides fun provideSiyerDao(db: KadiriDatabase)        = db.siyerDao()
    @Provides fun provideVurguNotDao(db: KadiriDatabase)     = db.vurguNotDao()
}
