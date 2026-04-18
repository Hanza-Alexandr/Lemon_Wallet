package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.model.repository.IStorageRepository
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.model.state.DomainState
import com.example.lemonwallet.ui.view.state.EditStorageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class EditStorageViewModel @Inject constructor(
    private val storageService: StorageService,
    private val storageRepo: IStorageRepository
) : ViewModel() {

    //Создаем приватный изменяемый поток состояния который будет хранить данные о вьюмодели
    private val _uiState = MutableStateFlow(EditStorageUiState())
    //Создаем неизменяемый поток состояния который будет предоставлять данные о вьюмодели во вне
    val uiState: StateFlow<EditStorageUiState> = _uiState.asStateFlow()

    fun loadStorage(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // Assuming we can get by ID from repo or service
            val storage = storageRepo.getById(id)
            if (storage != null) {
                _uiState.update {
                    it.copy(
                        storage = storage,
                        name = storage.name,
                        note = storage.note ?: "",
                        typeStorage = storage.typeStorage,
                        currency = storage.currency,
                        isStatistics = storage.isStatistics,
                        isArchive = storage.isArchive,
                        color = storage.color,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Storage not found") }
            }
        }
    }

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

    fun saveChanges() {
        val currentState = _uiState.value
        val currentStorage = currentState.storage ?: return

        viewModelScope.launch {
            val result = storageService.updateStorage(
                changingStorage = currentStorage,
                name = currentState.name,
                typeStorage = currentState.typeStorage,
                note = currentState.note,
                color = currentState.color,
                isStatistic = currentState.isStatistics,
                isArchive = currentState.isArchive
            )
            
            if (result is DomainState.Success) {
                _uiState.update { it.copy(isSaved = true) }
            } else if (result is DomainState.Error) {
                _uiState.update { it.copy(error = result.message) }
            }
        }
    }
    
    fun deleteStorage() {
        val currentStorage = _uiState.value.storage ?: return
        viewModelScope.launch {
            storageService.deleteStorage(currentStorage)
            _uiState.update { it.copy(isSaved = true) } // Navigate back
        }
    }
}
