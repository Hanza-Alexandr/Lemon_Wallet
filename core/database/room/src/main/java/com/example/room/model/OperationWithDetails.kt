package com.example.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.room.entity.CategoryRoomEntity
import com.example.room.entity.OperationEntity
import com.example.room.entity.StorageRoomEntity

data class OperationWithDetails(
    @Embedded
    val operation: OperationEntity,

    @Relation(
        parentColumn = "storage_id",
        entityColumn = "id"
    )
    val storage: StorageRoomEntity,

    @Relation(
        entity = CategoryRoomEntity::class,
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val categoryWithColor: CategoryWithColor?
)