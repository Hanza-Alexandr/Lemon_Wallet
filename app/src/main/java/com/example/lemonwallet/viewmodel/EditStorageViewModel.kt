package com.example.lemonwallet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.model.repository.IStorageRepository
import com.example.lemonwallet.model.service.ColorService
import com.example.lemonwallet.model.service.ColorUIService
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.model.state.DomainState
import com.example.lemonwallet.ui.view.state.ColorUIState
import com.example.lemonwallet.ui.view.state.EditStorageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



@HiltViewModel
class EditStorageViewModel @Inject constructor(
    private val storageService: StorageService,
    private val colorUIService: ColorUIService,
    private val colorService: ColorService,
) : ViewModel() {

    //Состояние интерфейса
    private val _uiState = MutableStateFlow(EditStorageUiState())
    val uiState: StateFlow<EditStorageUiState> = _uiState.asStateFlow()


    fun loadStorage(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val storage = storageService.getStorage(id)
            val colors = colorUIService.colorListForPicker.first()
            when(storage){
                is DomainState.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = "Storage not found") }
                }
                is DomainState.Success -> {
                    _uiState.update {
                        it.copy(
                            storage = storage.domain,
                            name = storage.domain.name,
                            note = storage.domain.note ?: "",
                            typeStorage = storage.domain.typeStorage,
                            availableColors = colors,
                            currency = storage.domain.currency,
                            isStatistics = storage.domain.isStatistics,
                            isArchive = storage.domain.isArchive,
                            color = storage.domain.color?.toUiState(),
                            isLoading = false
                        )
                    }
                }
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
                        storageService.updateStorage(
                        name = currentState.name,
                        typeStorage = currentState.typeStorage,
                        currency = currentState.currency,
                        note = currentState.note,
                        color = colorToSave, // <--- Обязательно передай полученный выше цвет!
                        changingStorage = currentState.storage!!,
                        isStatistic = currentState.isStatistics,
                        isArchive = currentState.isArchive,
                    )
                } catch (e: Exception) {
                    DomainState.Error(e.message ?: "Unknown error")
                }
            // 3. Последнее обновление стейта по результату
            _uiState.update {
                when (result) {
                    is DomainState.Success -> it.copy(isSaved = true, isLoading = false)
                    is DomainState.Error -> it.copy(error = result.message, isLoading = false)
                }
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
