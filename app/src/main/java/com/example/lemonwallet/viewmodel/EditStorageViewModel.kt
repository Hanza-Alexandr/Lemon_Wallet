package com.example.lemonwallet.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.model.repository.IStorageRepository
import com.example.lemonwallet.model.service.ColorService
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.model.state.DomainState
import com.example.lemonwallet.ui.view.state.ColorUIState
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
    private val storageRepo: IStorageRepository,
    private val colorService: ColorService,
) : ViewModel() {

    //Создаем приватный изменяемый поток состояния который будет хранить данные о вьюмодели
    private val _uiState = MutableStateFlow(EditStorageUiState())
    //Создаем неизменяемый поток состояния который будет предоставлять данные о вьюмодели во вне
    val uiState: StateFlow<EditStorageUiState> = _uiState.asStateFlow()

    fun loadStorage(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
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
                        color = storage.color?.toUiState(),
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
    
    fun onColorChange(newColor: ColorUIState?) {
        _uiState.update { it.copy(color = newColor) }
    }
    fun saveChanges() {
        val currentState = _uiState.value

        // Если имя пустое, можно сразу вернуть ошибку (пример валидации)
        if (currentState.name.isBlank()) {
            _uiState.update { it.copy(error = "Имя не может быть пустым") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // 1. Получаем объект ExistColor (из базы или сохраняем новый)
            val colorToSave: ExistColor? = when (val selectedColor = currentState.color) {
                is ColorUIState.DataBaseColor -> {
                    selectedColor.color
                }
                is ColorUIState.LocalSystemColor -> {
                    // Сохраняем системный цвет в БД, если его там еще нет
                    colorService.save(selectedColor)
                }
                null -> null
            }

            if (colorToSave == null) {
                _uiState.update { it.copy(error = "Ошибка подготовки цвета", isLoading = false) }
                return@launch
            }

            // 2. Создаем хранилище с полученным цветом
            val result = storageService.updateStorage(
                name = currentState.name,
                typeStorage = currentState.typeStorage,
                currency = currentState.currency,
                note = currentState.note,
                color = colorToSave, // Передаем уже сохраненный объект
                changingStorage = currentState.storage!!,
                isStatistic = currentState.isStatistics,
                isArchive = currentState.isArchive,
            )

            // 3. Обработка результата
            when (result) {
                is DomainState.Success -> {
                    _uiState.update { it.copy(isSaved = true, isLoading = false) }
                }
                is DomainState.Error -> {
                    _uiState.update { it.copy(error = result.message, isLoading = false) }
                }
                else -> {}
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
