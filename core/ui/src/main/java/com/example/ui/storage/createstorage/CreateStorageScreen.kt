package com.example.ui.storage.createstorage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.GlobalDetailStorageContent
import com.example.ui.them.MainDark


@Composable
fun CreateStorageScreen(){
    CreateStorageScreenContent(
        onBack = {
            TODO()
        }
    )
}
@Composable
fun CreateStorageScreenContent(
    onBack: () -> Unit,
    viewModel: CreateStorageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            focusManager.clearFocus()
            keyboardController?.hide()
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {

        TopBarCreateStorage(
            onBack = onBack,
            onSave = viewModel::saveNewStorage
        )

        GlobalDetailStorageContent(
            uiState = uiState,
            onNameChange = viewModel::onNameChange,
            onNoteChange = viewModel::onNoteChange,
            onTypeChange = viewModel::onTypeChange,
            onCurrencyChange = viewModel::onCurrencyChange,
            onColorChange = viewModel::onColorChange,
            onStatisticsChange = viewModel::onStatisticsChange,
            onArchiveChange = viewModel::onArchiveChange,
            onSaveColor = viewModel::onSaveColor,
            onDeleteColor = viewModel::deleteColor,
            onToggleDeleteMode = viewModel::toggleColorDeleteMode
        )
    }
}
