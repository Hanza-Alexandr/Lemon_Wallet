package com.example.detailstorage_screen.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detailstorage_screen.model.UIStatesDetailStorage
import com.example.domain.Currency
import com.example.domain.domainmodel.DomainColor
import com.example.domain.IEditStorage
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.NewDomainStorage
import com.example.domain.reposytory.IColorRepository
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.navigation.INavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateStorageViewModel @Inject constructor(
    private val navigator: INavigator,
    private val colorRepo: IColorRepository,
    private val storageRepo: IStorageRepository,
    private val settings: ISettingsRepository,
) : ViewModel(), IEditStorage {

    private val _uiState = MutableStateFlow(UIStatesDetailStorage())
    val uiState = _uiState.asStateFlow()
    init {
         viewModelScope.launch {
             _uiState.update { it.copy(availableColors = colorRepo.getAllColorsFlow().first()) }
         }
    }

    fun onSaveNewStorage(){
        viewModelScope.launch {
            if(
                _uiState.value.name == null||
                _uiState.value.typeStorage == null||
                _uiState.value.currency == null
            ){
                _uiState.update { it.copy(error = "Не все поля заполнены") }
            }
            else{
                storageRepo.saveStorage(
                    NewDomainStorage(
                        name = _uiState.value.name!!,
                        userId = settings.userIdFlow.first(),
                        currency = _uiState.value.currency!!,
                        typeStorage = _uiState.value.typeStorage!!,
                        note = _uiState.value.note,
                        color = _uiState.value.color
                    )
                )
            }
        }
        onBack()
    }

    fun onBack(){
        navigator.goBack()
    }

    override fun onNameChange(newName: String) {
        try {
            _uiState.update { it.copy(name = newName) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    override fun onNoteChange(newNote: String) {
        try {
            _uiState.update { it.copy(note = newNote) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    override fun onTypeChange(newType: TypeStorage) {
        try {
            _uiState.update { it.copy(typeStorage = newType) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

    override fun onCurrencyChange(newCurrency: Currency) {
        try {
            _uiState.update { it.copy(currency = newCurrency) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }

     fun onColorChange(newColor: DomainColor?) {
        try {
            _uiState.update { it.copy(color = newColor) }
        }
        catch (e: Exception){
            _uiState.update { it.copy(error = e.message) }
        }
    }
}
