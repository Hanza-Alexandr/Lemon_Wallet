package com.example.lemonwallet.di

import android.content.Context
import androidx.room.Room
import com.example.datastore.PreferencesDataStore
import com.example.domain.ICategoryRepository
import com.example.domain.IColorRepository
import com.example.domain.IStorageRepository
import com.example.domain.IUserSettingsRepository
import com.example.room.dao.CategoryDao
import com.example.room.dao.ColorDao
import com.example.room.dao.StorageDao
import com.example.room.database.AppDatabase
import com.example.room.repository.CategoryRoomRepository
import com.example.room.repository.ColorRoomRepository
import com.example.room.repository.StorageRoomRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStorageRepository(
        impl: StorageRoomRepository
    ): IStorageRepository

    @Binds
    @Singleton
    abstract fun bindColorRepository(
        impl: ColorRoomRepository
    ): IColorRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRoomRepository
    ): ICategoryRepository

    @Binds
    @Singleton
    abstract fun bindUserSettingsRepository(
        impl: PreferencesDataStore
    ): IUserSettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "room_database.db"
        )
            .build()
    }

    @Provides
    fun provideColorDao(db: AppDatabase): ColorDao = db.getColorDao()

    @Provides
    fun provideStorageDao(db: AppDatabase): StorageDao = db.getStorageDao()

    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao = db.getCategoryDao()
}
