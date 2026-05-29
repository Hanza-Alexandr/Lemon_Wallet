package com.example.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.room.dao.CategoryDao
import com.example.room.dao.ColorDao
import com.example.room.dao.OperationDao
import com.example.room.dao.StorageDao
import com.example.room.dao.TransferDao
import com.example.room.entity.CategoryRoomEntity
import com.example.room.entity.ColorRoomEntity
import com.example.room.entity.OperationRoomEntity
import com.example.room.entity.StorageRoomEntity
import com.example.room.entity.TransferRoomEntity

@Database(
    version = 12, // Incremented version since we added a new table
    entities = [
        StorageRoomEntity::class,
        ColorRoomEntity::class,
        CategoryRoomEntity::class,
        OperationRoomEntity::class,
        TransferRoomEntity::class
    ],
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun getStorageDao(): StorageDao
    abstract fun getColorDao(): ColorDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getOperationDao(): OperationDao
    abstract fun getTransferDao(): TransferDao

}