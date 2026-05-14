package com.example.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.settings.authorization.GetAuthStatusUseCase
import com.example.domain.settings.authorization.OnGuestAuthorizationUseCase
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationScreenViewModel @Inject constructor(
    private val navigator: INavigator,
    private val onGuestAuthorizationUseCase: OnGuestAuthorizationUseCase

    ): ViewModel(){
    fun loginWithYandexID(){
        TODO()
    }
    fun loginWithVKID(){
        TODO()
    }
    fun loginViaGoogle(){
        TODO()
    }
    fun loginByhNumberAndEmail(){
        TODO()
    }
    fun loginAsGuest(){
        navigator.navigateTo(
            NavigationRoute.MainScreen,
            NavigationRoute.OnBoarding,
            true)
        viewModelScope.launch {
            onGuestAuthorizationUseCase.invoke()
        }
    }
}