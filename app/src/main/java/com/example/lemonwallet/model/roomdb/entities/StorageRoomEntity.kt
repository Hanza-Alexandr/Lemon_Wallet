package com.example.lemonwallet.model.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.lemonwallet.model.domain.DomainColor
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage

@Entity(
    tableName = "storage",
    foreignKeys = [
        ForeignKey(
            entity = ColorRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["color_id"],
            onDelete = ForeignKey.CASCADE // Что делать, если цвет удалят?
        )
    ],
    indices = [Index(value = ["color_id"])] // Индексы ускоряют поиск
)
data class StorageRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    val currency: String,

    @ColumnInfo(name = "type_storage")
    val typeStorage: String,

    val note: String?, // Знак ? означает, что поле может быть NULL

    @ColumnInfo(name = "color_id")
    val colorId: Long?,

    @ColumnInfo(name = "is_statistics", defaultValue = "1")
    val isStatistics: Boolean,

    @ColumnInfo(name = "is_archive", defaultValue = "0")
    val isArchive: Boolean
)

data class StorageWithColor(
    val id: Long,
    val name: String,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    val currency: String,
    @ColumnInfo(name = "type_storage")
    val typeStorage: String,
    val note: String?,
    @ColumnInfo(name = "color_id")
    val colorId: Long?,
    @ColumnInfo(name = "is_statistics")
    val isStatistics: Boolean,
    @ColumnInfo(name = "is_archive")
    val isArchive: Boolean,

    // Поля из таблицы ColorEntity
    @ColumnInfo(name = "color_hex")
    val colorHex: String?,
    @ColumnInfo(name = "color_user_id")
    val colorUserId: Long?
){
    fun toDomain(color: ExistColor?): Storage{
        return Storage(
            id = id,
            name = name,
            userId = userId,
            currency = Currency.valueOf(currency),
            typeStorage = TypeStorage.valueOf(typeStorage),
            note = note,
            color = color,
            isStatistics = isStatistics,
            isArchive = isArchive
        )
    }
}

/**
 * CREATE TABLE StorageEntity (
 *     id INTEGER NOT NULL PRIMARY KEY,
 *     name TEXT NOT NULL,
 *     user_id INTEGER NOT NULL,
 *     currency TEXT NOT NULL,
 *     type_storage TEXT NOT NULL,
 *     note TEXT,
 *     color_id INTEGER NOT NULL REFERENCES ColorEntity(id),
 *     is_statistics INTEGER NOT NULL DEFAULT 1,
 *     is_archive INTEGER NOT NULL DEFAULT 0
 * );
 */