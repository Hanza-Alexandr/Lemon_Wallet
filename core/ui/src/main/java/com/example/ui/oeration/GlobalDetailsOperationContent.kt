package com.example.ui.oeration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.ui.oeration.components.StorageSection
import com.example.ui.oeration.components.AmountSection
import com.example.ui.oeration.components.CalculatorKeyboard
import com.example.ui.oeration.components.CategoriesSection
import com.example.ui.oeration.components.StorageUiModel
import com.example.ui.oeration.components.TransactionTypeSelector
import com.example.ui.oeration.components.UiModelCategory
import kotlin.reflect.KClass


@Composable
fun GlobalDetailsOperationContent(
    uiState: UIStatesDetailGeneralOperations,
    onChangeTypeOperation:  (KClass<out DomainOperation>) -> Unit,
    onFromStorageSelected: (StorageUiModel) -> Unit,
    onToStorageSelected: (StorageUiModel) -> Unit,
    onCategorySelected: (UiModelCategory) -> Unit,
    onStorageAdded: () -> Unit,
    onCategoryAdded: () -> Unit,
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
                            onCategoryAdded = onCategoryAdded
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

