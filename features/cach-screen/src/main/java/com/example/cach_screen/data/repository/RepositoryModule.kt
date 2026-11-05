package com.example.cach_screen.data.repository

import com.example.cach_screen.domain.repository.ICardHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCardHistoryRepository(
        repositoryImpl: CardHistoryRepositoryImpl
    ): ICardHistoryRepository
}