package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.service.StartScreenService
import com.example.lemonwallet.model.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(private val startScreenService: StartScreenService): ViewModel(){
    val stateAuth = startScreenService.stateAuth
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState.Loading
        )

    val isFirstOpeningApp = startScreenService.isFirstOpeningApp
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
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