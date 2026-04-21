package com.example.lemonwallet.model.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lemonwallet.model.roomdb.dao.ColorDao
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import com.example.lemonwallet.model.roomdb.entities.StorageRoomEntity

@Database(
    version = 4,
    entities = [
        StorageRoomEntity::class,
        ColorRoomEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStorageDao(): StorageDao
    abstract fun getColorDao(): ColorDao
}
