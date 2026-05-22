package com.example.domain.domainmodel

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed class DomainOperation{
    abstract val id: String
    abstract val userId: String
    abstract val amount: Long
    abstract val date: LocalDate
    abstract val time: LocalTime
}
abstract class GeneralOperation: DomainOperation(){
    abstract val storage: DomainStorage
    abstract val category: DomainCategory
    abstract val comment: String?

}
data class CreditOperation(
    override val id: String,
    override val userId: String,
    override val storage: DomainStorage,
    override val category: DomainCategory,
    override val amount: Long,
    override val date: LocalDate,
    override val time: LocalTime,
    override val comment: String?,

): GeneralOperation()

data class DebitOperation(
    override val id: String,
    override val userId: String,
    override val storage: DomainStorage,
    override val category: DomainCategory,
    override val amount: Long,
    override val date: LocalDate,
    override val time: LocalTime,
    override val comment: String?,

): GeneralOperation()

data class TransferOperation(
    override val id: String,
    override val userId: String,
    val fromStorage: DomainStorage,
    val toStorage: DomainStorage,
    override val amount: Long,
    override val date: LocalDate,
    override val time: LocalTime
): DomainOperation()

data class NewGeneralOperation(
    val userId: String,
    val storageId: String,
    val categoryId: String,
    val amount: Long,
    val isDebit: Boolean,
    val date: LocalDate,
    val time: LocalTime,
    val comment: String?
)

data class NewTransferOperation(
    val userId: String,
    val fromStorageId: String,
    val toStorageId: String,
    val amount: Long,
    val date: LocalDate,
    val time: LocalTime
)