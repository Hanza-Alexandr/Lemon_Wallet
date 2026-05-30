package com.example.detailoperation_screen.model

import com.example.domain.domainmodel.NewDomainOperation
import com.example.domain.domainmodel.NewGeneralOperation
import com.example.domain.domainmodel.NewTransferOperation
import com.example.detailoperation_screen.ui.components.StorageUiModel
import com.example.detailoperation_screen.ui.components.UiModelCategory
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class UIStatesDetailGeneralOperations(
    val result: Long = 0,
    val expression: String ="",
    val uiStateTypeOperation: UiStateTypeOperation?= null,
    val date: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val time: LocalTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time,
    val note: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)

sealed class UiStateTypeOperation{
    data class TransferUiStateTypeOperation(
        val fromStorageList: List<StorageUiModel> = emptyList(),
        val toStorageList: List<StorageUiModel> = emptyList(),
    ): UiStateTypeOperation()

    data class GeneralOperationUiStateTypeOperation(
        val isDebit: Boolean,
        val categories: List<UiModelCategory> = emptyList(),
        val storageList: List<StorageUiModel> = emptyList()
    ): UiStateTypeOperation()
}

fun UIStatesDetailGeneralOperations.toNewDomainOperation(): NewDomainOperation {
    val currentType = this.uiStateTypeOperation
        ?: throw IllegalStateException("Тип операции не выбран")

    return when (currentType) {
        is UiStateTypeOperation.GeneralOperationUiStateTypeOperation -> {
            // Валидация выбора категории
            val selectedCategory = currentType.categories.find { it.isSelect }?.category
                ?: throw IllegalStateException("Пожалуйста, выберите категорию")

            // Валидация выбора кошелька
            val selectedStorage = currentType.storageList.find { it.isSelected }?.storage
                ?: throw IllegalStateException("Пожалуйста, выберите кошелек")

            // Проверка суммы
            if (this.result <= 0) throw IllegalStateException("Сумма должна быть больше нуля")

            NewGeneralOperation(
                userId = selectedStorage.userId,
                storageId = selectedStorage.id,
                categoryId = selectedCategory.id,
                amount = this.result,
                isDebit = currentType.isDebit,
                date = this.date,
                time = this.time,
                comment = this.note
            )
        }

        is UiStateTypeOperation.TransferUiStateTypeOperation -> {
            // Валидация кошелька "Откуда"
            val fromStorage = currentType.fromStorageList.find { it.isSelected }?.storage
                ?: throw IllegalStateException("Выберите кошелек списания")

            // Валидация кошелька "Куда"
            val toStorage = currentType.toStorageList.find { it.isSelected }?.storage
                ?: throw IllegalStateException("Выберите кошелек зачисления")

            // Проверка на идентичность кошельков
            if (fromStorage.id == toStorage.id) {
                throw IllegalStateException("Кошельки списания и зачисления должны быть разными")
            }

            if (this.result <= 0) throw IllegalStateException("Сумма перевода должна быть больше нуля")

            NewTransferOperation(
                userId = fromStorage.userId,
                fromStorageId = fromStorage.id,
                toStorageId = toStorage.id,
                amount = this.result,
                comment = this.note,
                date = this.date,
                time = this.time
            )
        }
    }
}