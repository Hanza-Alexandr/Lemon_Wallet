package com.example.ui.multimenu

import androidx.lifecycle.ViewModel
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MultiMenuViewModel @Inject constructor(private val navigator: INavigator): ViewModel() {

    fun toApiScreen(){
        navigator.navigateTo(NavigationRoute.ApiScreen)
    }
    fun toBleScreen(){
        navigator.navigateTo(NavigationRoute.BleScreen)
    }
}