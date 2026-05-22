package com.example.ui.storage.editstorage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.storage.GlobalDetailStorageContent
import com.example.ui.storage.createstorage.TopBarCreateStorage
import com.example.ui.them.MainDark


@Composable
fun EditStorageScreen(
    viewModel: EditStorageViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {

        TopBarEditStorage(
            onBack = viewModel::onBack,
            onSave =  viewModel::onSaveChangesStorage ,
            onDelete = viewModel::onDelete
        )

        GlobalDetailStorageContent(
            uiState = viewModel.uiState.collectAsState().value,
            onNameChange = viewModel::onNameChange,
            onNoteChange = viewModel::onNameChange,
            onTypeChange = viewModel::onTypeChange,
            onCurrencyChange = viewModel::onCurrencyChange
        )
    }
}
