package com.example.cach_screen.ui

import com.example.cach_screen.domain.IHistoryCardRepository
import com.example.cach_screen.network.NetworkMock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CachScreenModule {

    @Provides
    @Singleton
    fun provideHistoryCardRepository(): IHistoryCardRepository {
        return NetworkMock()
    }
}
