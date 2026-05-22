package com.example.room

import com.example.room.entity.ColorRoomEntity
import java.util.UUID

object DefaultColor {
    fun getItems(userID: String): List<ColorRoomEntity> {
        return listOf(
            ColorRoomEntity(
                id = UUID.randomUUID().toString(),
                userId = userID,
                hexCode = "#FF0000",
            ),
            ColorRoomEntity(
                id = UUID.randomUUID().toString(),
                userId = userID,
                hexCode = "#00FF00",
            ),
            ColorRoomEntity(
                id = UUID.randomUUID().toString(),
                userId = userID,
                hexCode = "#0000FF",
            )
        )
    }
}