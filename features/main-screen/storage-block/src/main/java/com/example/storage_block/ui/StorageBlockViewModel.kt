package com.example.storage_block.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.domain.usecase.GetStorageBalanceUseCase
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import com.example.storage_block.model.UiForStorageBlock
import com.example.storage_block.usecases.OnSelectStorageInStorageBlockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageBlockViewModel @Inject constructor(
    private val navigator: INavigator,
    private val onSelectStorageInStorageBlockUseCase: OnSelectStorageInStorageBlockUseCase,
    private val storageRepo: IStorageRepository,
    private val getStorageBalanceUseCase: GetStorageBalanceUseCase,
    private val setting: ISettingsRepository


    ): ViewModel(){

    private val storagesFlow = storageRepo.getAllStoragesFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    private val allBalancesFlow = getStorageBalanceUseCase.allBalancesFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyMap()
    )
    private val selectIdsFlow = setting.idSelectedStorageFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptySet()
    )


    val storageUIList= combine(
        storagesFlow.onEach { Log.i("LOOOOOOOOGG","⚡ сторедж счтетов 1 ДЕРНУЛСЯ (новое значение): $it") },
        allBalancesFlow.onEach { Log.i("LOOOOOOOOGG","⚡ баланс счтетов 1 ДЕРНУЛСЯ (новое значение): $it") },
        selectIdsFlow.onEach { Log.i("LOOOOOOOOGG","⚡ селект счтетов 1 ДЕРНУЛСЯ (новое значение): $it") },
    ) { storages, balancesMap, selectIds->
        storages.map { storage ->
            UiForStorageBlock(
                storage = storage,
                balance = balancesMap[storage.id] ?: 0L,
                isSelected = if (selectIds.find { it == storage.id } != null) true else false
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
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