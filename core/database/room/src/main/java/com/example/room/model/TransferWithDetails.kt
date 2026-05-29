package com.example.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.utils.toDomainDateTime
import com.example.room.entity.StorageRoomEntity
import com.example.room.entity.TransferRoomEntity

data class TransferWithDetails(
    @Embedded
    val transfer: TransferRoomEntity,

    @Relation(
        entity = StorageRoomEntity::class,
        parentColumn = "from_storage_id",
        entityColumn = "id"
    )
    val fromStorage: StorageWithColor,

    @Relation(
        entity = StorageRoomEntity::class,
        parentColumn = "to_storage_id",
        entityColumn = "id"
    )
    val toStorage: StorageWithColor
)

fun TransferWithDetails.toDomain(): TransferOperation{
    return TransferOperation(
        id = transfer.id,
        userId = transfer.userId,
        fromStorage = DomainStorage(
            id = fromStorage.storage.id,
            userId = fromStorage.storage.userId,
            name = fromStorage.storage.name,
            currency = Currency.valueOf(fromStorage.storage.currency),
            typeStorage = TypeStorage.valueOf(fromStorage.storage.accountType),
            note = fromStorage.storage.note,
            color = if (fromStorage.color!=null)DomainColor(
                id = fromStorage.color.id,
                userId = fromStorage.color.userId,
                hex = fromStorage.color.hexCode
            )else null
        ),
        toStorage = DomainStorage(
            id = toStorage.storage.id,
            userId = toStorage.storage.userId,
            name = toStorage.storage.name,
            currency = Currency.valueOf(toStorage.storage.currency),
            typeStorage = TypeStorage.valueOf(toStorage.storage.accountType),
            note = toStorage.storage.note,
            color = if (toStorage.color!=null)DomainColor(
                id = toStorage.color.id,
                userId = toStorage.color.userId,
                hex = toStorage.color.hexCode
            )else null
        ),
        amount = transfer.amount,
        comment = transfer.comment,
        date = transfer.dateTime.toDomainDateTime().date,
        time = transfer.dateTime.toDomainDateTime().time
    )
}
