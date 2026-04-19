package com.example.lemonwallet.model.hiltmodul

import android.content.Context
import androidx.room.Room
import com.example.lemonwallet.model.repository.ColorRoomRepository
import com.example.lemonwallet.model.repository.IColorRepository
import com.example.lemonwallet.model.repository.IStorageRepository
import com.example.lemonwallet.model.repository.StorageRoomRepository
import com.example.lemonwallet.model.roomdb.dao.ColorDao
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.database.AppDatabase
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
    abstract fun bindStorageRepository(
        impl: StorageRoomRepository // Что Hilt должен СОЗДАТЬ
    ): IStorageRepository           // Под видом КАКОГО интерфейса отдать

    @Binds
    abstract fun bindColorRepository(
        impl: ColorRoomRepository
    ): IColorRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "room_database.db" // Ваше имя БД
        )

            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    fun provideStorageDao(db: AppDatabase): StorageDao {
        return db.getStorageDao()
    }
    @Provides
    fun provideColorDao(db: AppDatabase): ColorDao {
        return db.getColorDao()
    }
}


