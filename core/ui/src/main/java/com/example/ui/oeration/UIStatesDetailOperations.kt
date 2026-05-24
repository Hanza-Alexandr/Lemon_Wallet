package com.example.ui.oeration

import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainStorage
import com.example.ui.oeration.components.StorageUiModel
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
        val categories: List<DomainCategory> = emptyList(),
        val storageList: List<StorageUiModel> = emptyList()
    ): UiStateTypeOperation()
}


