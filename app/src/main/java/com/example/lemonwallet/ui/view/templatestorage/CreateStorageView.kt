package com.example.lemonwallet.ui.view.templatestorage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lemonwallet.ui.theme.MainDark
import com.example.lemonwallet.ui.view.state.CreateStorageUiState
import com.example.lemonwallet.ui.view.topbars.TopBarCreateStorage
import com.example.lemonwallet.ui.view.topbars.TopBarEditStorage
import com.example.lemonwallet.viewmodel.CreateStorageViewModel
import com.example.lemonwallet.viewmodel.EditStorageViewModel

@Composable
fun CreateStorageView(
    onBack: () -> Unit,
    viewModel: CreateStorageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
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
        )
    }

}