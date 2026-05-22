package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.NewDomainStorage
import com.example.domain.utils.SynStatus
import java.util.UUID

@Entity(
    tableName = "storages",
    foreignKeys = [
        ForeignKey(
            entity = ColorRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["color_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["color_id"]),Index(value = ["user_id"])]
)
data class StorageRoomEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "account_type") val accountType: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "color_id") val colorId: String?,
    //Service Info
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: String = SynStatus.LOCAL_ONLY.toString()
)

fun StorageRoomEntity.toDomain(color: DomainColor?): DomainStorage {
    return DomainStorage(
        id = id,
        name = name,
        userId = userId,
        currency = Currency.valueOf(currency),
        typeStorage = TypeStorage.valueOf(accountType),
        note = null,
        color = color
    )
}

fun NewDomainStorage.toRoomEntity(): StorageRoomEntity {
    return StorageRoomEntity(
        name = name,
        userId = userId,
        currency = currency.name,
        accountType = typeStorage.name,
        note = note,
        colorId = color?.id
    )
}

fun DomainStorage.toRoomEntity(): StorageRoomEntity{
    return StorageRoomEntity(
        id = id,
        name = name,
        userId = userId,
        currency = currency.name,
        accountType = typeStorage.name,
        note = note,
        colorId = color?.id
    )
}