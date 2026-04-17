package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(private val storageService: StorageService): ViewModel() {


    val storageList: StateFlow<List<Storage>?> = storageService.getFlowStorageList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
            initialValue = null
        )

    val storageBlockSelectState = storageService.stateSelectedStorages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
        initialValue = emptySet()
    )

    fun onSelect(isLongClick: Boolean, index: Int) {
        viewModelScope.launch {
            storageService.onSelect(isLongClick, index)
        }
    }



}