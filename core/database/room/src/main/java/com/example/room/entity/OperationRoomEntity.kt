package com.example.room.entity

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.NewGeneralOperation
import com.example.domain.utils.SynStatus
import com.example.domain.utils.dateTimeToEpoch
import com.example.domain.utils.toDomainDateTime
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

@Entity(
    tableName = "operations",
    foreignKeys = [
        ForeignKey(
            entity = StorageRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["storage_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryRoomEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["storage_id"]),
        Index(value = ["category_id"]),
        Index(value = ["user_id", "date_time"])
    ]
)
data class OperationRoomEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "storage_id") val storageId: String,
    @ColumnInfo(name = "category_id") val categoryId: String,
    @ColumnInfo(name = "amount") val amount: Long,           // Хранение в копейках (10000 = 100.00)
    @ColumnInfo(name = "isDebit") val isDebit: Boolean,       // true - доход, false - расход
    @ColumnInfo(name = "date_time") val dateTime: Long,         // Unix timestamp
    @ColumnInfo(name = "comment") val comment: String? = null,
    //Service Info
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: String = SynStatus.LOCAL_ONLY.toString()
)

fun OperationRoomEntity.toDomain(storage: DomainStorage, category: DomainCategory): GeneralOperation {

    if (isDebit) {
        return DebitOperation(
            id = id,
            userId = userId,
            storage = storage,
            category = category,
            amount = amount,
            date = dateTime.toDomainDateTime().date,
            time = dateTime.toDomainDateTime().time,
            comment = comment
        )
    }
    else{
        return CreditOperation(
            id = id,
            userId = userId,
            storage = storage,
            category = category,
            amount = amount,
            date = dateTime.toDomainDateTime().date,
            time = dateTime.toDomainDateTime().time,
            comment = comment
        )
    }
}
fun NewGeneralOperation.toRoomEntity(): OperationRoomEntity {
    // Конвертируем дату и время из домена обратно в Long (Unix timestamp)
    // Если у вас нет готового метода, можно использовать:
    // LocalDateTime(date, time).toInstant(TimeZone.UTC).toEpochMilliseconds()
    val timestamp = dateTimeToEpoch(this.date, this.time)

    return OperationRoomEntity(
        userId = this.userId,
        storageId = this.storageId,   // Берем ID из объекта storage
        categoryId = this.categoryId, // Берем ID из объекта category
        amount = this.amount,
        isDebit = this is DebitOperation, // Определяем тип операции по классу
        dateTime = timestamp,
        comment = this.comment,
        updatedAt = System.currentTimeMillis(),
        syncStatus = SynStatus.LOCAL_ONLY.toString() // По умолчанию для новых/измененных
    )
}

