package com.example.main_screen.ui

import androidx.lifecycle.ViewModel
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val navigator: INavigator
): ViewModel() {
    fun onMainBottomClick(){
        navigator.navigateTo(NavigationRoute.CreateOperation)
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