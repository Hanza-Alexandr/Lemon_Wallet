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
}