package com.kadiri.elkitabi.core.di

import android.content.Context
import androidx.room.Room
import com.kadiri.elkitabi.core.data.local.KadiriDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KadiriDatabase =
        Room.databaseBuilder(context, KadiriDatabase::class.java, "kadiri_database.db")
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides fun provideZikirDao(db: KadiriDatabase)        = db.zikirDao()
    @Provides fun provideZikirSessionDao(db: KadiriDatabase) = db.zikirSessionDao()
    @Provides fun provideKitapDao(db: KadiriDatabase)        = db.kitapDao()
    @Provides fun provideAmelDao(db: KadiriDatabase)         = db.amelDao()
}
