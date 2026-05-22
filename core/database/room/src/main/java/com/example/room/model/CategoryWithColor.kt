package com.example.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.domainmodel.DomainCategory
import com.example.room.entity.CategoryRoomEntity
import com.example.room.entity.ColorRoomEntity
import com.example.room.entity.toDomain

data class CategoryWithColor(
    @Embedded
    val category: CategoryRoomEntity,

    @Relation(
        parentColumn = "color_id",
        entityColumn = "id"
    )
    val color: ColorRoomEntity?
)

fun CategoryWithColor.toDomain(): DomainCategory {
    return DomainCategory(
        id = category.id,
        userId = category.userId,
        name = category.name,
        color = color?.toDomain(),
        icon = category.icon,
        parenId = category.parentId
    )
}