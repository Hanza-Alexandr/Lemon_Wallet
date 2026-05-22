package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.NewDomainCategory
import com.example.domain.utils.SynStatus
import java.util.UUID

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = ColorRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["color_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = CategoryRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["color_id"]),
        Index(value = ["parent_id"]),
        Index(value = ["user_id"])

    ]
)
data class CategoryRoomEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "icon") val icon: String,

    @ColumnInfo(name = "color_id") val colorId: String?,
    @ColumnInfo(name = "parent_id") val parentId: String?, // Self-reference
    //Service Info
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: String = SynStatus.LOCAL_ONLY.toString()
)

fun CategoryRoomEntity.toDomain(color: DomainColor?): DomainCategory{
    return DomainCategory(
        id= id,
        userId = userId,
        name= name,
        color = color,
        icon = icon,
        parenId = parentId
    )
}
fun NewDomainCategory.toRoomEntity(): CategoryRoomEntity{
    return CategoryRoomEntity(
        userId = userId,
        name = name,
        icon = icon,
        colorId = color?.id,
        parentId = parenId
    )
}
fun DomainCategory.toRoomEntity(): CategoryRoomEntity {
    return CategoryRoomEntity(
        id = id,
        userId = userId,
        name = name,
        icon = icon,
        colorId = color?.id,
        parentId = parenId
    )
}

