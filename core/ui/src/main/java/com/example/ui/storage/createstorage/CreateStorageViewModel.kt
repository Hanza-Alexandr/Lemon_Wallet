package com.example.ui.storage.createstorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.state.DomainState
import com.example.domain.ColorUIState
import com.example.domain.Currency
import com.example.domain.ExistColor
import com.example.domain.IEditStorage
import com.example.domain.NewColor
import com.example.domain.TypeStorage
import com.example.domain.UserColor
import com.example.domain.toUiState
import com.example.domain.usecase.ColorService
import com.example.domain.usecase.StorageService
import com.example.ui.DefaultStateDetailsStorage
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
    private val colorService: ColorService
) : ViewModel(), IEditStorage {
    private val _uiState = MutableStateFlow(DefaultStateDetailsStorage())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            colorService.colorListForPicker.collect { colors ->
                _uiState.update { it.copy(availableColors = colors) }
            }
        }
    }

    override fun onSaveColor(newColor: NewColor){
        viewModelScope.launch {
            val color = colorService.save(newColor)
            if (color!=null){
                val uiColor = color.toUiState()
                val newList = _uiState.value.availableColors.toMutableList()
                newList.add(uiColor)
                _uiState.update {
                    it.copy(
                        availableColors = newList
                    )
                }
            }
            else{
                _uiState.update { it.copy(error = "Ошибка сохранения цвета") }
            }
        }
    }

    override fun onNameChange(newName: String) {
        _uiState.update { it.copy(name = newName) }
    }

    override fun onNoteChange(newNote: String) {
        _uiState.update { it.copy(note = newNote) }
    }

    override fun onTypeChange(newType: TypeStorage) {
        _uiState.update { it.copy(typeStorage = newType) }
    }

    override fun onCurrencyChange(newCurrency: Currency) {
        _uiState.update { it.copy(currency = newCurrency) }
    }

    override fun onStatisticsChange(value: Boolean) {
        _uiState.update { it.copy(isStatistics = value) }
    }

    override fun onArchiveChange(value: Boolean) {
        _uiState.update { it.copy(isArchive = value) }
    }

    override fun onColorChange(newColor: ColorUIState?) {
        _uiState.update { it.copy(color = newColor) }
    }

    override fun toggleColorDeleteMode(enabled: Boolean) {
        _uiState.update { it.copy(isColorDeleteMode = enabled) }
    }

    override fun deleteColor(colorUiState: ColorUIState) {
        if (colorUiState is ColorUIState.DataBaseColor && colorUiState.color is UserColor) {
            viewModelScope.launch {
                colorService.delete(colorUiState.color as UserColor)
                _uiState.update {
                    it.copy(
                        availableColors = it.availableColors.filter { it != colorUiState }
                    )
                }
            }
        }
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
