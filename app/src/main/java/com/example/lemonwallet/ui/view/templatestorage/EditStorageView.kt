package com.example.lemonwallet.ui.view.templatestorage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.lemonwallet.R
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.ui.theme.MainDark
import com.example.lemonwallet.ui.view.state.EditStorageUiState
import com.example.lemonwallet.ui.view.topbars.TopBarEditStorage
import com.example.lemonwallet.viewmodel.EditStorageViewModel

@Composable
fun EditStorageView(
    storageId: Long,
    onBack: () -> Unit,
    viewModel: EditStorageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current


    LaunchedEffect(storageId) {
        viewModel.loadStorage(storageId)
    }

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

        //TopBar нужен

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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {

        TopBarEditStorage(
            onBack = onBack,
            onDelete = viewModel::deleteStorage,
            onSave = viewModel::saveChanges
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

@Preview(showBackground = true)
@Composable
fun EditStoragePreview() {
    GlobalDetailStorageContent(
        uiState = EditStorageUiState(
            name = "Наличные",
            note = "В кошельке",
            isLoading = false,
            isStatistics = true,
            isArchive = false,
        ),
        onNameChange = {},
        onNoteChange = {},
        onTypeChange = {},
        onCurrencyChange = {},
        onColorChange = {},
        onStatisticsChange = {},
        onArchiveChange = {}
    )
}
