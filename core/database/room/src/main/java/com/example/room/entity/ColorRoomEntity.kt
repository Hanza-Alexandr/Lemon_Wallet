package com.example.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.NewDomainColor
import com.example.domain.utils.SynStatus
import java.util.UUID

@Entity(tableName = "colors")
data class ColorRoomEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "hex_code") val hexCode: String,
    //Service Info
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: String = SynStatus.LOCAL_ONLY.toString()
)

fun ColorRoomEntity.toDomain(): DomainColor {
    return DomainColor(
        id = id,
        userId = userId,
        hex = hexCode,
    )
}

fun NewDomainColor.toRoomEntity(): ColorRoomEntity {
    return ColorRoomEntity(
        userId = userId,
        hexCode = hex,
    )
}
