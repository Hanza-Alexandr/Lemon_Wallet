package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.service.StartScreenService
import com.example.lemonwallet.model.state.DataLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel без UI. Служит для обработки события - открытие приложения. т.е авторизирован ли пользователь и открыл ли он приложение в первый раз
 */
@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val startScreenService: StartScreenService,
): ViewModel(){

    
    val stateAuth = startScreenService.stateAuth
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataLoadingState.Loading
        )

    val isFirstOpeningApp = startScreenService.isFirstOpeningApp
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataLoadingState.Loading
        )

    val dataIsReady = stateAuth.combine(isFirstOpeningApp){ authState, isFirstOpeningApp ->
        authState !is DataLoadingState.Loading && isFirstOpeningApp !is DataLoadingState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun markFirstAppOpeningCompleted(){
        viewModelScope.launch {
            startScreenService.markFirstAppOpeningCompleted()
        }
    }
    fun logIn(id: Int){
        viewModelScope.launch {
            startScreenService.logIn(id)
        }
    }
}
