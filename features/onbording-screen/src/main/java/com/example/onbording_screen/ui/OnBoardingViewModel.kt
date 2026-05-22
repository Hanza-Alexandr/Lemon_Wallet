package com.example.onbording_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.settings.onboarding.OnBoardingStatusInteractor
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val navigator: INavigator,
    private val onBoardingStatusInteractor: OnBoardingStatusInteractor,
): ViewModel(){

    fun onFinished(){
        setFalseStatus()
        navigator.navigateTo(
            route = NavigationRoute.AuthorizationScreen
        )

    }
    private fun setFalseStatus(){
        viewModelScope.launch {
            onBoardingStatusInteractor.setFalseStatus()
        }
    }
}