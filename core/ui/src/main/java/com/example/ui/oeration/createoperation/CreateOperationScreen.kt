package com.example.ui.oeration.createoperation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.oeration.GlobalDetailsOperationContent
import com.example.ui.storage.GlobalDetailStorageContent
import com.example.ui.storage.createstorage.TopBarCreateStorage
import com.example.ui.them.MainDark

@Composable
fun CreateOperationScreen(
    viewModel: CreateOperationViewModel = hiltViewModel()
){
    SideEffect {
        Log.d("RECOMPOSITION", "MyComponent finished recomposition")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {
        TopBarCreateOperation (
            onBack = viewModel::onBack,
            onSave = viewModel::onSave
        )

        GlobalDetailsOperationContent(
            uiState = viewModel.uiState.collectAsState().value,
            onChangeTypeOperation = viewModel::onChangeTypeOperation,
            onFromStorageSelected = viewModel::onFromStorageSelected,
            onToStorageSelected = viewModel::onToStorageSelected,
            onCategorySelected = viewModel::onCategorySelected,
            onKeyClick = viewModel::onKeyClick,
            onStorageAdded = viewModel::onStorageAdded,
            onCategoryAdded = viewModel::onCategoryAdded
        )

    }
}