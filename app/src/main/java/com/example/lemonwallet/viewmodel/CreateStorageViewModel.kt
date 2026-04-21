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
import kotlinx.coroutines.cancel
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
        if (currentState.name.isBlank()) {
            _uiState.update { it.copy(error = "Имя не может быть пустым") }
            return
        }
        viewModelScope.launch {
            val colorToSave: ExistColor? = when (val selectedColor = currentState.color) {
                is ColorUIState.DataBaseColor -> selectedColor.color
                is ColorUIState.LocalSystemColor -> {
                    val colorSaved = colorService.save(selectedColor)
                    if (colorSaved==null) {
                        _uiState.update { it.copy(error = "Ошибка сохранения цвета") }
                        cancel()
                    }
                    colorSaved
                }
                else -> null
            }
            val result = try {
                storageService.createStorage(
                    name = currentState.name,
                    typeStorage = currentState.typeStorage,
                    note = currentState.note,
                    color = colorToSave, // Передаем уже сохраненный объект
                    currency = currentState.currency,
                )
            }catch (e: Exception){
                DomainState.Error(e.message ?: "Unknown error")
            }

            _uiState.update {
                when (result) {
                    is DomainState.Success -> it.copy(isSaved = true, isLoading = false)
                    is DomainState.Error -> it.copy(error = result.message, isLoading = false)
                }
            }
        }
    }
}
