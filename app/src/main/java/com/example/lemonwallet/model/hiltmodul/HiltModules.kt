package com.example.lemonwallet.model.hiltmodul

import android.content.Context
import androidx.room.Room
import com.example.lemonwallet.model.repository.IStorageRepository
import com.example.lemonwallet.model.repository.StorageRoomRepository
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.database.AppDatabase
import com.example.lemonwallet.model.roomdb.database.DatabaseCallback
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
            .addCallback(DatabaseCallback()) // Ваша коллбек-логика
            .build()
    }

    @Provides
    fun provideStorageDao(db: AppDatabase): StorageDao {
        return db.getStorageDao()
    }
}