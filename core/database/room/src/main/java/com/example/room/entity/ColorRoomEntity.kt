package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.ExistColor
import com.example.domain.NewColor
import com.example.domain.SystemColor
import com.example.domain.UserColor


@Entity(tableName = "color")
data class ColorRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long?,
    @ColumnInfo(name = "hex_code") val hexCode: String
){
    fun toDomain(): ExistColor {
        return if (userId == null) SystemColor(id, hexCode)
        else UserColor(id, hexCode, userId)
    }
}

fun ExistColor.toRoomEntityColor(userId: Long): ColorRoomEntity{
    when (this){
        is SystemColor -> {
            return ColorRoomEntity(
                id = id,
                userId = null,
                hexCode = hex
            )
        }
        is UserColor -> {
            return ColorRoomEntity(
                id = id,
                userId = userId,
                hexCode = hex
            )
        }
        else -> throw IllegalArgumentException("❌Неизвестный тип цвета")
    }
}

fun NewColor.toRoomEntityColor(userId: Long): ColorRoomEntity{
    return ColorRoomEntity(
        id = 0,
        userId = userId,
        hexCode = hex
    )
}
