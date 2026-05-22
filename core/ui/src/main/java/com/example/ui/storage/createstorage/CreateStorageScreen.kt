package com.example.ui.storage.createstorage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.ui.storage.GlobalDetailStorageContent
import com.example.ui.them.MainDark

@Composable
fun CreateStorageScreen(
    viewModel: CreateStorageViewModel = hiltViewModel(),
) {
    val name: String? by rememberSaveable() { mutableStateOf(null)}
    val note: String? by rememberSaveable() { mutableStateOf(null)}
    val typeStorage: TypeStorage? by rememberSaveable() { mutableStateOf(null)}
    val currency: Currency? by rememberSaveable() { mutableStateOf(null)}
    val error: String? by rememberSaveable() { mutableStateOf(null)}

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
            onNoteChange = viewModel::onNameChange,
            onTypeChange = viewModel::onTypeChange,
            onCurrencyChange = viewModel::onCurrencyChange
        )
    }
}
