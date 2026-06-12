package com.kadiri.elkitabi.core.di

import com.kadiri.elkitabi.features.zikir.data.repository.ZikirRepositoryImpl
import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import com.kadiri.elkitabi.features.kitap.data.repository.KitapRepositoryImpl
import com.kadiri.elkitabi.features.kitap.domain.repository.KitapRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindZikirRepository(impl: ZikirRepositoryImpl): ZikirRepository

    @Binds
    @Singleton
    abstract fun bindKitapRepository(impl: KitapRepositoryImpl): KitapRepository
}
