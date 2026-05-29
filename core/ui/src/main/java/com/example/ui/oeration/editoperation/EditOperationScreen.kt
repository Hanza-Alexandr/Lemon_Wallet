package com.example.ui.oeration.editoperation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.oeration.GlobalDetailsOperationContent
import com.example.ui.them.MainDark

@Composable
fun EditOperationScreen(
    viewModel: EditOperationViewModel = hiltViewModel()
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {
        TopBarEditOperation(
            onBack = viewModel::onBack,
            onSave = viewModel::onUpdate,
            onDelete = viewModel::onDelete,
        )

        GlobalDetailsOperationContent(
            uiState = viewModel.uiState.collectAsState().value,
            onChangeTypeOperation = viewModel::onChangeTypeOperation,
            onFromStorageSelected = viewModel::onFromStorageSelected,
            onToStorageSelected = viewModel::onToStorageSelected,
            onCategorySelected = viewModel::onCategorySelected,
            onKeyClick = viewModel::onKeyClick,
            onCategoryAdded = viewModel::onCategoryAdded,
            onStorageAdded = viewModel::onStorageAdded
        )

    }
}