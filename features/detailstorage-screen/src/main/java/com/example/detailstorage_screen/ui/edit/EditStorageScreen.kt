package com.example.detailstorage_screen.ui.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.detailstorage_screen.model.UIStatesDetailStorage
import com.example.detailstorage_screen.ui.GlobalDetailStorageContent
import com.example.ui.them.MainDark

@Composable
fun EditStorageScreen(
    viewModel: EditStorageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(UIStatesDetailStorage())

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
                uiState = uiState,
                onNameChange = viewModel::onNameChange,
                onNoteChange = viewModel::onNoteChange,
                onTypeChange = viewModel::onTypeChange,
                onCurrencyChange = viewModel::onCurrencyChange,
                onColorChange = viewModel::onColorChange
            )
        }
    }

