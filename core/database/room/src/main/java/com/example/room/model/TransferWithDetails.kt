package com.example.room.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.room.entity.StorageRoomEntity
import com.example.room.entity.TransferRoomEntity

data class TransferWithDetails(
    @Embedded
    val transfer: TransferRoomEntity,

    @Relation(
        parentColumn = "from_storage_id",
        entityColumn = "id"
    )
    val fromStorage: StorageRoomEntity,

    @Relation(
        parentColumn = "to_storage_id",
        entityColumn = "id"
    )
    val toStorage: StorageRoomEntity
)
