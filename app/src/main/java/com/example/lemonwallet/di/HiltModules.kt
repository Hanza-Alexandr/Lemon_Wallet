package com.example.lemonwallet.di

import android.content.Context
import androidx.room.Room
import com.example.datastore.PreferencesDataStore
import com.example.domain.reposytory.ICategoryRepository
import com.example.domain.reposytory.IColorRepository
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.navigation.INavigator
import com.example.lemonwallet.NavigatorImpl
import com.example.room.dao.CategoryDao
import com.example.room.dao.ColorDao
import com.example.room.dao.StorageDao
import com.example.room.database.AppRoomDatabase
import com.example.room.database.RoomDataBaseCallBack
import com.example.room.repository.CategoryRoomRepository
import com.example.room.repository.ColorRoomRepository
import com.example.room.repository.StorageRoomRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

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
    ): ISettingsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        // Создаем Scope, который живет всё время жизни приложения
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        callback: RoomDataBaseCallBack,
    ): AppRoomDatabase {
        var instance: AppRoomDatabase? = null
        instance = Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            "room_database.db"
        )
            .addCallback(callback)
            .fallbackToDestructiveMigration()
            .build()
        return instance
    }

    @Provides
    fun provideColorDao(db: AppRoomDatabase): ColorDao = db.getColorDao()

    @Provides
    fun provideStorageDao(db: AppRoomDatabase): StorageDao = db.getStorageDao()

    @Provides
    fun provideCategoryDao(db: AppRoomDatabase): CategoryDao = db.getCategoryDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    @Singleton
    abstract fun bindNavigator(impl: NavigatorImpl): INavigator
}