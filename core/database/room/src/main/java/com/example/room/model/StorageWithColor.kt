package com.example.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainStorage
import com.example.room.entity.ColorRoomEntity
import com.example.room.entity.StorageRoomEntity
import com.example.room.entity.toDomain

data class StorageWithColor(
    @Embedded
    val storage: StorageRoomEntity,

    @Relation(
        parentColumn = "color_id",
        entityColumn = "id"
    )
    val color: ColorRoomEntity?
)

fun StorageWithColor.toDomain(): DomainStorage{
    return DomainStorage(
        id = storage.id,
        name = storage.name,
        userId = storage.userId,
        currency = Currency.valueOf(storage.currency),
        typeStorage = TypeStorage.valueOf(storage.accountType),
        note = storage.note,
        color = color?.toDomain()
    )
}