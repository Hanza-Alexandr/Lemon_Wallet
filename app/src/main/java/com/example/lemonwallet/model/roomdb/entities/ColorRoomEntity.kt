package com.example.lemonwallet.model.roomdb.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lemonwallet.model.domain.DomainColor
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.model.domain.UserColor


@Entity(tableName = "color")
data class ColorRoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "user_id") val userId: Long?,
    @ColumnInfo(name = "hex_code") val hexCode: String
){
    fun toDomain(): ExistColor{
        return if (userId == null) SystemColor(id,hexCode)
        else UserColor(id, hexCode, userId)
    }
}
/**
 * CREATE TABLE ColorEntity (
 *     id INTEGER NOT NULL PRIMARY KEY,
 *     user_id INTEGER,
 *     hex_code TEXT NOT NULL
 * );
 */