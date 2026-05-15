package com.example.ui.storage.createstorage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.storage.GlobalDetailStorageContent
import com.example.ui.them.MainDark

@Composable
fun CreateStorageScreen(
    createStorageViewModel: CreateStorageViewModel = hiltViewModel(),
) {
    val uiState by createStorageViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            createStorageViewModel.onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {

        TopBarCreateStorage(
            onBack = createStorageViewModel::onBack,
            onSave = createStorageViewModel::saveNewStorage
        )

        GlobalDetailStorageContent(
            uiState = uiState,
            onNameChange = createStorageViewModel::onNameChange,
            onNoteChange = createStorageViewModel::onNoteChange,
            onTypeChange = createStorageViewModel::onTypeChange,
            onCurrencyChange = createStorageViewModel::onCurrencyChange,
            onColorChange = createStorageViewModel::onColorChange,
            onStatisticsChange = createStorageViewModel::onStatisticsChange,
            onArchiveChange = createStorageViewModel::onArchiveChange,
            onSaveColor = createStorageViewModel::onSaveColor,
            onDeleteColor = createStorageViewModel::deleteColor,
            onToggleDeleteMode = createStorageViewModel::toggleColorDeleteMode
        )
    }
}
