package com.example.ui.colorpikerrow

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigator
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.NewDomainColor
import com.example.domain.reposytory.IColorRepository
import com.example.navigation.INavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ColorPickerRowViewModel @Inject constructor(
    private val colorRepository: IColorRepository,
): ViewModel() {
    val availableColors = colorRepository.getAllColorsFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
    private val _isDeleteMode = MutableStateFlow(false)
    val isDeleteMode = _isDeleteMode.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()


    fun onAddNewColorClick() {
        _showDialog.value = true
    }

    fun hideAddDialog() {
        _showDialog.value = false
    }
    fun onDeleteColor(color: DomainColor) {

    }
    fun createNewColor(newColor: NewDomainColor){
        viewModelScope.launch {
            colorRepository.saveColor(newColor)
        }
    }
    fun onToggleDeleteMode(isDeleteMode: Boolean){
        _isDeleteMode.value= isDeleteMode
    }
}

