package com.example.lemonwallet.ui.view.templatestorage

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lemonwallet.ui.view.topbars.TopBarEditStorage
import com.example.lemonwallet.viewmodel.EditStorageViewModel

@Composable
fun EditStorageView(
    storageId: Long,
    onBack: () -> Unit,
    viewModel: EditStorageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(storageId) {
        viewModel.loadStorage(storageId)
    }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopBarEditStorage(
                onBack = onBack,
                onSave = { viewModel.saveChanges() },
                onDelete = { viewModel.deleteStorage() }
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
