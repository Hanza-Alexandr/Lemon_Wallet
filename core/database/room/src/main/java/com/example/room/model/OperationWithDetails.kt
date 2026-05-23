package com.example.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.utils.toDomainDateTime
import com.example.room.entity.CategoryRoomEntity
import com.example.room.entity.OperationRoomEntity
import com.example.room.entity.StorageRoomEntity

data class OperationWithDetails(
    @Embedded
    val operation: OperationRoomEntity,

    @Relation(
        entity = StorageRoomEntity::class,
        parentColumn = "storage_id",
        entityColumn = "id"
    )
    val storage: StorageWithColor,

    @Relation(
        entity = CategoryRoomEntity::class,
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryWithColor
)

fun OperationWithDetails.toDomain(): GeneralOperation{
    if (this.operation.isDebit){
        return DebitOperation(
            id = operation.id,
            userId = operation.userId,
            storage = DomainStorage(
                id = storage.storage.id,
                userId = storage.storage.userId,
                name = storage.storage.name,
                currency = Currency.valueOf(storage.storage.currency),
                typeStorage = TypeStorage.valueOf(storage.storage.accountType),
                note = storage.storage.note,
                color = if (storage.color!=null)DomainColor(
                    id = storage.color.id,
                    userId = storage.color.userId,
                    hex = storage.color.hexCode
                )else null
            ),
            category = DomainCategory(
                id = category.category.id,
                userId = category.category.userId,
                name = category.category.name,
                color = if (category.color!=null)DomainColor(
                    id = category.color.id,
                    userId = category.color.userId,
                    hex = category.color.hexCode
                )else null,
                icon = category.category.icon,
                parenId = category.category.parentId
            ),
            amount = operation.amount,
            date = operation.dateTime.toDomainDateTime().date,
            time = operation.dateTime.toDomainDateTime().time,
            comment = operation.comment
        )
    }
    else{
        return CreditOperation(
            id = operation.id,
            userId = operation.userId,
            storage = DomainStorage(
                id = storage.storage.id,
                userId = storage.storage.userId,
                name = storage.storage.name,
                currency = Currency.valueOf(storage.storage.currency),
                typeStorage = TypeStorage.valueOf(storage.storage.accountType),
                note = storage.storage.note,
                color = if (storage.color!=null)DomainColor(
                    id = storage.color.id,
                    userId = storage.color.userId,
                    hex = storage.color.hexCode
                )else null
            ),
            category = DomainCategory(
                id = category.category.id,
                userId = category.category.userId,
                name = category.category.name,
                color = if (category.color!=null)DomainColor(
                    id = category.color.id,
                    userId = category.color.userId,
                    hex = category.color.hexCode
                )else null,
                icon = category.category.icon,
                parenId = category.category.parentId
            ),
            amount = operation.amount,
            date = operation.dateTime.toDomainDateTime().date,
            time = operation.dateTime.toDomainDateTime().time,
            comment = operation.comment
        )
    }
}
