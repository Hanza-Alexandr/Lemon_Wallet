package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.model.repository.IStorageRepository
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.model.state.DomainState
import com.example.lemonwallet.ui.view.state.CreateStorageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CreateStorageViewModel @Inject constructor(
    private val storageService: StorageService,
) : ViewModel() {

    //Создаем приватный изменяемый поток состояния который будет хранить данные о вьюмодели
    private val _uiState = MutableStateFlow(CreateStorageUiState())
    val uiState = _uiState.asStateFlow()
    //Создаем неизменяемый поток состояния который будет предоставлять данные о вьюмодели во вне

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    fun onNoteChange(newNote: String) {
        _uiState.update { it.copy(note = newNote) }
    }

    fun onTypeChange(newType: TypeStorage) {
        _uiState.update { it.copy(typeStorage = newType) }
    }

    fun onCurrencyChange(newCurrency: Currency) {
        _uiState.update { it.copy(currency = newCurrency) }
    }

    fun onStatisticsChange(value: Boolean) {
        _uiState.update { it.copy(isStatistics = value) }
    }

    fun onArchiveChange(value: Boolean) {
        _uiState.update { it.copy(isArchive = value) }
    }

    fun onColorChange(newColor: ExistColor) {
        _uiState.update { it.copy(color = newColor) }
    }

    fun saveNewStorage() {
        val currentState = _uiState.value
        
        viewModelScope.launch {
            val result = storageService.createStorage(
                name = currentState.name,
                typeStorage = currentState.typeStorage,
                note = currentState.note,
                color = currentState.color,
                currency = currentState.currency,
            )

            if (result is DomainState.Success) {
                _uiState.update { it.copy(isSaved = true) }
            } else if (result is DomainState.Error) {
                _uiState.update { it.copy(error = result.message) }
            }
        }
    }
}