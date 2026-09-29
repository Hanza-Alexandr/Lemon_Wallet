package com.kaspersky.kaspresso.lemonwallet.mainscreen

import com.example.domain.reposytory.ICategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
// Это ключевая часть: мы говорим Hilt использовать этот модуль вместо реального во время тестов
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = []
)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(): ICategoryRepository {
        // Возвращаем мок, чтобы не зависеть от БД или сети
        return mockk(relaxed = true)
    }
}

