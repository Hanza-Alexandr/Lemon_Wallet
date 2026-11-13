package com.example.bdui_screen.di

import com.example.bdui_screen.data.BDUIRepositoryImpl
import com.example.bdui_screen.domain.IBDUIRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BDUIModule {

    @Binds
    @Singleton
    abstract fun bindBDUIRepository(
        bduiRepositoryImpl: BDUIRepositoryImpl
    ): IBDUIRepository
}
