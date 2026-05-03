package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.Category
import com.example.domain.CategoryStructure
import com.example.domain.ExistColor
import com.example.domain.NeedCategory
import com.example.domain.Owner


@Entity(
    tableName = "category",
    foreignKeys = [
        ForeignKey(
            entity = ColorRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["color_id"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = CategoryRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["parent_category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("color_id"),
        Index("parent_category_id")
    ]
)
data class CategoryRoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "color_id")
    val colorId: Long,
    @ColumnInfo(name = "path_icon")
    val pathIcon: String,
    val need: String,
    @ColumnInfo(name = "is_hide")
    val isHide: Boolean,
    @ColumnInfo(name = "user_id")
    val userId: Long?, // NULL for system categories
    @ColumnInfo(name = "parent_category_id")
    val parentCategoryId: String? // NULL for root categories
)

data class CategoryWithColor(
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "color_id")
    val colorId: Long,
    @ColumnInfo(name = "path_icon")
    val pathIcon: String,
    @ColumnInfo(name = "need")
    val need: String,
    @ColumnInfo(name = "is_hide")
    val isHide: Boolean,
    @ColumnInfo(name = "user_id")
    val userId: Long?,
    @ColumnInfo(name = "parent_category_id")
    val parentCategoryId: String?,

    // Fields from ColorRoomEntity
    @ColumnInfo(name = "color_hex")
    val colorHex: String?,
    @ColumnInfo(name = "color_user_id")
    val colorUserId: Long?
) {
    fun toDomain(color: ExistColor): Category {
        val owner = if (userId == null) Owner.System else Owner.User(userId)
        val structure = if (parentCategoryId == null) CategoryStructure.Root else CategoryStructure.Child(parentCategoryId)
        return Category(
            id = id,
            name = name,
            color = color,
            icon = pathIcon,
            need = NeedCategory.valueOf(need),
            isHidden = isHide,
            owner = owner,
            structure = structure
        )
    }
}
