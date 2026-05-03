package com.example.lemonwallet.model.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lemonwallet.model.roomdb.dao.CategoryDao
import com.example.lemonwallet.model.roomdb.dao.ColorDao
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.entities.CategoryRoomEntity
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import com.example.lemonwallet.model.roomdb.entities.StorageRoomEntity

@Database(
    version = 6, // Incremented version since we added a new table
    entities = [
        StorageRoomEntity::class,
        ColorRoomEntity::class,
        CategoryRoomEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getStorageDao(): StorageDao
    abstract fun getColorDao(): ColorDao
    abstract fun getCategoryDao(): CategoryDao
}
