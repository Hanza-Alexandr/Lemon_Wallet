package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.repository.PreferencesDataStore
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.service.StorageService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainViewModel(private val storageService: StorageService): ViewModel() {

    val storageList: StateFlow<List<Storage>?> = storageService.getFlowStorageList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
            initialValue = null
        )

    val storageBlockSelectState = storageService.UIStorageService().stateSelectedStorages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
        initialValue = emptySet()
    )

    fun onSelect(isLongClick: Boolean, index: Int) {
        viewModelScope.launch {
            storageService.UIStorageService().onSelect(isLongClick, index)
        }
    }


}