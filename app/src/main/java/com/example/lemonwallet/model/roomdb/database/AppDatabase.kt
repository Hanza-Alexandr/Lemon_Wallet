package com.example.lemonwallet.model.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import com.example.lemonwallet.model.roomdb.entities.StorageRoomEntity

@Database(
    version = 1,
    entities = [
        StorageRoomEntity::class,
        ColorRoomEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getStorageDao(): StorageDao

    companion object RoomDataBase {

        private lateinit var applicationContext: Context

        fun init(context: Context) {
            applicationContext = context
        }

        val appDatabase: AppDatabase by lazy {
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "room_database.db")
                .addCallback(DatabaseCallback())
                .build()
        }
    }
}


// Внутренний класс для заполнения данными
private class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Эти SQL-запросы выполнятся только ПРИ ПЕРВОМ создании файла БД

        // 1. Тестовые цвета
        db.execSQL("INSERT INTO color (id, user_id, hex_code) VALUES (1, 101, '#FF5733')")
        db.execSQL("INSERT INTO color (id, user_id, hex_code) VALUES (2, 101, '#33FF57')")

        // 2. Тестовые кошельки (проверьте, чтобы имена колонок совпадали с вашим Entity)
        db.execSQL("""
    INSERT INTO storage (name, user_id, currency, type_storage, color_id, is_statistics, is_archive) 
    VALUES 
        ('Cash', -1, 'RUB', 'CARD', 1, 1, 0),
        ('SBER', -1, 'RUB', 'CARD', 2, 1, 0),
        ('VTB', -1, 'RUB', 'CARD', 2, 1, 0),
        ('T-Back', -1, 'RUB', 'CARD', 1, 1, 0)
""")
    }
}
