package com.example.storage_block

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import com.example.storage_block.usecases.GetFlowUiForStorageBlockUseCase
import com.example.storage_block.usecases.OnSelectStorageInStorageBlockUseCase
import com.example.storage_block.model.UiForStorageBlock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageBlockViewModel @Inject constructor(
    private val navigator: INavigator,
    getFlowUiForStorageBlockUseCase: GetFlowUiForStorageBlockUseCase,
    private val onSelectStorageInStorageBlockUseCase: OnSelectStorageInStorageBlockUseCase
): ViewModel(){
    val storageUIList: StateFlow<List<UiForStorageBlock>?> = getFlowUiForStorageBlockUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
            initialValue = null
        )
    fun onStorageClick(idStorage: String){
        viewModelScope.launch {
            onSelectStorageInStorageBlockUseCase(isLongClick = false, idStorage)
        }
    }
    fun onStorageLongClick(idStorage: String){
        viewModelScope.launch {
            onSelectStorageInStorageBlockUseCase(isLongClick = true, idStorage)
        }
    }
    fun onEditStorageClick(storageId: String){
        navigator.navigateTo(NavigationRoute.EditStorage(storageId))
    }
    fun onAddStorageClick(){
        navigator.navigateTo(NavigationRoute.CreateStorage)
    }
}