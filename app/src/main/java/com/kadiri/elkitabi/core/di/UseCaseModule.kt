package com.kadiri.elkitabi.core.di

import com.kadiri.elkitabi.features.zikir.domain.repository.ZikirRepository
import com.kadiri.elkitabi.features.zikir.domain.usecase.GetGunlukHedefUseCase
import com.kadiri.elkitabi.features.zikir.domain.usecase.GetZikirIstatistikUseCase
import com.kadiri.elkitabi.features.zikir.domain.usecase.GetZikirListeUseCase
import com.kadiri.elkitabi.features.zikir.domain.usecase.OzelZikirEkleUseCase
import com.kadiri.elkitabi.features.zikir.domain.usecase.SaveZikirSessionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides @Singleton
    fun provideGetZikirListeUseCase(repo: ZikirRepository) = GetZikirListeUseCase(repo)

    @Provides @Singleton
    fun provideSaveZikirSessionUseCase(repo: ZikirRepository) = SaveZikirSessionUseCase(repo)

    @Provides @Singleton
    fun provideGetZikirIstatistikUseCase(repo: ZikirRepository) = GetZikirIstatistikUseCase(repo)

    @Provides @Singleton
    fun provideGetGunlukHedefUseCase(repo: ZikirRepository) = GetGunlukHedefUseCase(repo)

    @Provides @Singleton
    fun provideOzelZikirEkleUseCase(repo: ZikirRepository) = OzelZikirEkleUseCase(repo)
}
