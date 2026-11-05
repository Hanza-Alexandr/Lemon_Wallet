package com.example.cach_screen.data.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideLocalCardHistoryDataSource(): ILocalCardHistoryDataSource {
        return LocalMock()
    }
}