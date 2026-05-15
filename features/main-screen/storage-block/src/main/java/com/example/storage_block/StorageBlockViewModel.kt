package com.example.storage_block

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.Storage
import com.example.domain.usecase.StorageService
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageBlockViewModel @Inject constructor(
    private val navigator: INavigator,
    private val storageService: StorageService
): ViewModel(){
    val storageList: StateFlow<List<Storage>?> = storageService.getFlowStorageList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
            initialValue = null
        )

    val storageBlockSelectState = storageService.stateSelectedStorages.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
        initialValue = emptySet()
    )

    fun onStorageClick(index: Int){
        viewModelScope.launch {
            storageService.onSelect(isLongClick = false, index)
        }
    }
    fun onStorageLongClick(index: Int){
        viewModelScope.launch {
            storageService.onSelect(isLongClick = true, index)
        }
    }
    fun onEditStorageClick(storageId: Long){
        navigator.navigateTo(NavigationRoute.EditStorage(storageId))
    }
    fun onAddStorageClick(){
        navigator.navigateTo(NavigationRoute.CreateStorage)
    }



}