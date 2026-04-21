package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.model.service.ColorService
import com.example.lemonwallet.model.service.ColorUIService
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.model.state.DomainState
import com.example.lemonwallet.ui.view.state.ColorUIState
import com.example.lemonwallet.ui.view.state.CreateStorageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateStorageViewModel @Inject constructor(
    private val storageService: StorageService,
    private val colorUIService: ColorUIService,
    private val colorService: ColorService
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateStorageUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            colorUIService.colorListForPicker.collect { colors ->
                _uiState.update { it.copy(availableColors = colors) }
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

    fun saveNewStorage() {
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
            val result = storageService.createStorage(
                name = currentState.name,
                typeStorage = currentState.typeStorage,
                note = currentState.note,
                color = colorToSave, // Передаем уже сохраненный объект
                currency = currentState.currency,
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
}
