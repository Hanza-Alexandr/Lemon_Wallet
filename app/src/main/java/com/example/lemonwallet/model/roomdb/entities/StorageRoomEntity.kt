package com.example.lemonwallet.model.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
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
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["color_id"])]
)
data class StorageRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "type_storage")
    val typeStorage: String,

    @ColumnInfo(name = "note")
    val note: String?,

    @ColumnInfo(name = "color_id")
    val colorId: Long?,

    @ColumnInfo(name = "is_statistics", defaultValue = "1")
    val isStatistics: Boolean,

    @ColumnInfo(name = "is_archive", defaultValue = "0")
    val isArchive: Boolean
)

data class StorageWithColor(
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name = "currency")
    val currency: String,
    @ColumnInfo(name = "type_storage")
    val typeStorage: String,
    @ColumnInfo(name = "note")
    val note: String?,
    @ColumnInfo(name = "color_id")
    val colorId: Long?,
    @ColumnInfo(name = "is_statistics")
    val isStatistics: Boolean,
    @ColumnInfo(name = "is_archive")
    val isArchive: Boolean,

    // Fields from ColorEntity
    @ColumnInfo(name = "color_hex")
    val colorHex: String?,
    @ColumnInfo(name = "color_user_id")
    val colorUserId: Long?
){
    fun toDomain(color: ExistColor?): Storage {
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
