package com.example.onbording_screen.ui

import androidx.lifecycle.ViewModel
import com.example.domain.state.DataLoadingState
import com.example.domain.usecase.StartScreenService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val startScreenService: StartScreenService,
): ViewModel(){

    /**
    val stateAuth = startScreenService.stateAuth
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = DataLoadingState.Loading
        )

    val isFirstOpeningApp = startScreenService.isFirstOpeningApp
        .stateIn(scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = DataLoadingState.Loading
        )

    val dataIsReady = stateAuth.combine(isFirstOpeningApp){ authState, isFirstOpeningApp ->
        authState !is DataLoadingState.Loading && isFirstOpeningApp !is DataLoadingState.Loading
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5000),
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
    */
}