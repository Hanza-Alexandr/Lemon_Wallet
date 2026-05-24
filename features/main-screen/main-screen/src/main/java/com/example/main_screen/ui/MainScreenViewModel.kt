package com.example.main_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetSelectStorageUseCase
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val navigator: INavigator,
    private val getSelectStorageUseCase: GetSelectStorageUseCase
): ViewModel() {
    fun onMainBottomClick(){
        viewModelScope.launch {
            val a = getSelectStorageUseCase.invoke().first()
            if (a.size ==1){
                navigator.navigateTo(NavigationRoute.CreateOperation(a[0].id))
            }
        }
    }
    /*

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

    fun onSelect(isLongClick: Boolean, index: Int) {
        viewModelScope.launch {
            storageService.onSelect(isLongClick, index)
        }
    }

     */


}