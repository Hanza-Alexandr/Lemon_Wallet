package com.example.detailoperation_screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.detailoperation_screen.model.UIStatesDetailGeneralOperations
import com.example.detailoperation_screen.model.UiStateTypeOperation
import com.example.domain.domainmodel.DomainOperation
import com.example.detailoperation_screen.ui.components.StorageSection
import com.example.detailoperation_screen.ui.components.AmountSection
import com.example.detailoperation_screen.ui.components.CalculatorKeyboard
import com.example.detailoperation_screen.ui.components.CategoriesSection
import com.example.detailoperation_screen.ui.components.StorageUiModel
import com.example.detailoperation_screen.ui.components.TransactionTypeSelector
import com.example.detailoperation_screen.ui.components.UiModelCategory
import kotlin.reflect.KClass


@Composable
fun GlobalDetailsOperationContent(
    uiState: UIStatesDetailGeneralOperations,
    onChangeTypeOperation:  (KClass<out DomainOperation>) -> Unit,
    onFromStorageSelected: (StorageUiModel) -> Unit,
    onToStorageSelected: (StorageUiModel) -> Unit,
    onCategorySelected: (UiModelCategory) -> Unit,
    onStorageAdded: () -> Unit,
    onMoreCategory: () -> Unit,
    onKeyClick: (String) -> Unit

){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            if (uiState.uiStateTypeOperation !==null){
                AmountSection(
                    expression = uiState.expression,
                    result = uiState.result
                )
                TransactionTypeSelector(
                    selectIndex = when(uiState.uiStateTypeOperation){
                        is UiStateTypeOperation.GeneralOperationUiStateTypeOperation -> if (uiState.uiStateTypeOperation.isDebit) 0 else 1
                        is UiStateTypeOperation.TransferUiStateTypeOperation -> 2
                        else -> 0
                    },
                    onChangeType = onChangeTypeOperation
                )
                if (uiState.uiStateTypeOperation is UiStateTypeOperation.TransferUiStateTypeOperation){
                    Column() {
                        StorageSection(
                            storages = uiState.uiStateTypeOperation.fromStorageList,
                            onStorageSelected = onFromStorageSelected,
                            onStorageAdded = onStorageAdded
                        )
                        StorageSection(
                            storages = uiState.uiStateTypeOperation.toStorageList,
                            onStorageSelected = onToStorageSelected,
                            onStorageAdded = onStorageAdded
                        )
                    }
                }
                else if(uiState.uiStateTypeOperation is UiStateTypeOperation.GeneralOperationUiStateTypeOperation){
                    Column() {
                        CategoriesSection(
                            topCategories = uiState.uiStateTypeOperation.categories,
                            onCategorySelected = onCategorySelected,
                            onMoreCategory = onMoreCategory
                        )
                        StorageSection(
                            storages = uiState.uiStateTypeOperation.storageList,
                            onStorageSelected = onFromStorageSelected,
                            onStorageAdded = onStorageAdded
                        )
                    }
                }
            }

        }
        CalculatorKeyboard(
            onKeyClick = onKeyClick
        )
    }
}

