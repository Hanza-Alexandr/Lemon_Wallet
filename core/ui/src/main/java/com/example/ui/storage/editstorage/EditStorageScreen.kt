package com.example.ui.storage.editstorage

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.Storage
import com.example.ui.storage.GlobalDetailStorageContent


@Composable
fun EditStorageScreen(
    viewModel: EditStorageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            viewModel.onBack()
        }
    }

    Scaffold(
        topBar = {
            TopBarEditStorage(
                onBack = viewModel::onBack,
                onSave =  viewModel::saveChanges ,
                onDelete = viewModel::deleteStorage
            )
        }
    ) { padding ->
        GlobalDetailStorageContent(
            modifier = Modifier.padding(padding),
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
