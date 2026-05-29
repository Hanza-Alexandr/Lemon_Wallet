package com.example.room.entity
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.NewTransferOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.utils.SynStatus
import com.example.domain.utils.dateTimeToEpoch
import com.example.domain.utils.toDomainDateTime
import java.util.UUID

@Entity(
    tableName = "transfers",
    foreignKeys = [
        ForeignKey(
            entity = StorageRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["from_storage_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StorageRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["to_storage_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["from_storage_id"]),
        Index(value = ["to_storage_id"]),
        Index(value = ["user_id", "date_time"])
    ]
)
data class TransferRoomEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "from_storage_id") val fromStorageId: String,
    @ColumnInfo(name = "to_storage_id") val toStorageId: String,
    @ColumnInfo(name = "amount") val amount: Long, // Хранение в копейках (10000 = 100.00)
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "date_time") val dateTime: Long,
    //Service Info
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: String = SynStatus.LOCAL_ONLY.toString()
)

fun TransferRoomEntity.toDomain(fromStorage: DomainStorage, toStorage: DomainStorage): TransferOperation {
    return TransferOperation(
        id = id,
        userId = userId,
        fromStorage = fromStorage,
        toStorage = toStorage,
        amount = amount,
        comment = comment,
        date = dateTime.toDomainDateTime().date,
        time = dateTime.toDomainDateTime().time
    )
}

fun NewTransferOperation.toRoomEntity(): TransferRoomEntity {
    // Конвертируем дату и время в Long timestamp
    val timestamp = dateTimeToEpoch(this.date, this.time)

    return TransferRoomEntity(
        userId = this.userId,
        fromStorageId = this.fromStorageId, // ID кошелька списания
        toStorageId = this.toStorageId,     // ID кошелька зачисления
        amount = this.amount,
        comment = this.comment,
        dateTime = timestamp,
        updatedAt = System.currentTimeMillis(),
        isDeleted = false,
        syncStatus = SynStatus.LOCAL_ONLY.toString()
    )
}

fun TransferOperation.toRoomEntity(): TransferRoomEntity{
    // Конвертируем дату и время в Long timestamp
    val timestamp = dateTimeToEpoch(this.date, this.time)

    return TransferRoomEntity(
        id = this.id,
        userId = this.userId,
        fromStorageId = this.fromStorage.id, // ID кошелька списания
        toStorageId = this.fromStorage.id,     // ID кошелька зачисления
        amount = this.amount,
        comment = this.comment,
        dateTime = timestamp,
        updatedAt = System.currentTimeMillis(),
        isDeleted = false,
        syncStatus = SynStatus.LOCAL_ONLY.toString()
    )
}
