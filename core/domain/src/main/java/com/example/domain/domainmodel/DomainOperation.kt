package com.example.domain.domainmodel

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed class NewDomainOperation{
    abstract val userId: String
    abstract val amount: Long
    abstract val date: LocalDate
    abstract val time: LocalTime
}
 sealed class DomainOperation: NewDomainOperation(){
    abstract val id: String
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
    override val userId: String,
    val storageId: String,
    val categoryId: String,
    override val amount: Long,
    val isDebit: Boolean,
    override val date: LocalDate,
    override val time: LocalTime,
    val comment: String?
): NewDomainOperation()

data class NewTransferOperation(
    override val userId: String,
    val fromStorageId: String,
    val toStorageId: String,
    override val amount: Long,
    override val date: LocalDate,
    override val time: LocalTime
): NewDomainOperation()