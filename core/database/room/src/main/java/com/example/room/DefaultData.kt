package com.example.room

import com.example.room.entity.CategoryRoomEntity
import com.example.room.entity.ColorRoomEntity
import java.util.UUID

object DefaultData {
    fun getColorItems(userID: String): List<ColorRoomEntity> {
        return listOf(
            ColorRoomEntity(
                userId = userID,
                hexCode = "#FF0000",
            ),
            ColorRoomEntity(
                userId = userID,
                hexCode = "#00FF00",
            ),
            ColorRoomEntity(
                userId = userID,
                hexCode = "#0000FF",
            )
        )
    }
    fun getCategoryItems(userId: String): List<CategoryRoomEntity> {
        return listOf(
            CategoryRoomEntity(
                userId = userId,
                name = "Еда и напитки",
                icon = "",
                colorId = null,
                parentId =  null,
            ),
            CategoryRoomEntity(
                userId = userId,
                name = "Покупки",
                icon = "",
                colorId = null,
                parentId =  null,
            ),
            CategoryRoomEntity(
                userId = userId,
                name = "Доход",
                icon = "",
                colorId = null,
                parentId =  null,
            ),
        )
    }

}

