package com.example.detailstorage_screen.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.detailstorage_screen.ui.GlobalDetailStorageContent
import com.example.ui.them.MainDark

@Composable
fun CreateStorageScreen(
    viewModel: CreateStorageViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {

        TopBarCreateStorage(
            onBack = viewModel::onBack,
            onSave = viewModel::onSaveNewStorage
        )

        GlobalDetailStorageContent(
            uiState = viewModel.uiState.collectAsState().value,
            onNameChange = viewModel::onNameChange,
            onNoteChange = viewModel::onNoteChange,
            onTypeChange = viewModel::onTypeChange,
            onCurrencyChange = viewModel::onCurrencyChange,
            onColorChange = viewModel::onColorChange
        )
    }
}
