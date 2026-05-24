package com.example.room

import com.example.room.entity.CategoryRoomEntity
import com.example.room.entity.ColorRoomEntity
import java.util.UUID


object DefaultData {

    // Генерируем фиксированные ID для цветов, чтобы на них можно было сослаться в категориях
    private val colorIdFood = UUID.randomUUID().toString()
    private val colorIdShopping = UUID.randomUUID().toString()
    private val colorIdIncome = UUID.randomUUID().toString()

    fun getColorItems(userID: String): List<ColorRoomEntity> {
        return listOf(
            ColorRoomEntity(
                id = colorIdFood, // Явно указываем ID
                userId = userID,
                hexCode = "#FF5722", // Оранжево-красный (Еда)
            ),
            ColorRoomEntity(
                id = colorIdShopping,
                userId = userID,
                hexCode = "#2196F3", // Синий (Покупки)
            ),
            ColorRoomEntity(
                id = colorIdIncome,
                userId = userID,
                hexCode = "#4CAF50", // Зеленый (Доход)
            )
        )
    }
    fun getCategoryItems(userId: String): List<CategoryRoomEntity> {
        return listOf(
            CategoryRoomEntity(
                id = UUID.randomUUID().toString(),
                userId = userId,
                name = "Еда и напитки",
                icon = "fastfood", // Пример названия иконки
                colorId = colorIdFood, // Привязываем к цвету
                parentId = null,
            ),
            CategoryRoomEntity(
                id = UUID.randomUUID().toString(),
                userId = userId,
                name = "Покупки",
                icon = "shopping_cart",
                colorId = colorIdShopping, // Привязываем к цвету
                parentId = null,
            ),
            CategoryRoomEntity(
                id = UUID.randomUUID().toString(),
                userId = userId,
                name = "Доход",
                icon = "payments",
                colorId = colorIdIncome, // Привязываем к цвету
                parentId = null,
            ),
        )
    }

}

