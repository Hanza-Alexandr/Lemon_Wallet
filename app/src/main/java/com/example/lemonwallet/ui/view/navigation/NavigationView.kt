package com.example.lemonwallet.ui.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lemonwallet.ui.view.authscreen.AuthScreenView
import com.example.lemonwallet.ui.view.mainscreen.MainScreenView
import com.example.lemonwallet.ui.view.startscreen.StartScreenView
import com.example.lemonwallet.model.state.AuthorizationState
import com.example.lemonwallet.viewmodel.StartScreenViewModel


@Composable
fun AppNavigation (){
    val startScreenVM: StartScreenViewModel = hiltViewModel()
    val dataIsReady = startScreenVM.dataIsReady.collectAsState()
    if (!dataIsReady.value){
        return
    }

    val navController = rememberNavController()
    val isFirstOpen by startScreenVM.isFirstOpeningApp.collectAsState()
    val authState by startScreenVM.stateAuth.collectAsState()

    // UI-логика вычисления стартового экрана после гарантированного прихода данных
    val startScreen =when {
        isFirstOpen == true -> Screen.FirstOpened
        authState is AuthorizationState.Authorization || authState is AuthorizationState.Guest -> Screen.MainScreen
        else -> Screen.AuthScreen
    }

    NavHost(
        navController = navController,
        startDestination = startScreen,
    ){
        composable<Screen.FirstOpened> {
            StartScreenView {
                startScreenVM.markFirstAppOpeningCompleted() //Запись в данные приложения что пользователь открыл приложение что бы стартовый экран не показывался вновь
                navController.navigate(Screen.AuthScreen) {
                    popUpTo(Screen.FirstOpened) { inclusive = true }
                }
            }
        }
        composable<Screen.AuthScreen> {
            AuthScreenView(
                onLoginSuccess = {

                },
                onContinueAsGuest = {
                    startScreenVM.logIn(-1)
                    navController.navigate(Screen.MainScreen) {
                        popUpTo(Screen.AuthScreen) { inclusive = true }
                    }
                })
        }
        composable<Screen.MainScreen> {
            MainScreenView()
        }
    }

}

@Serializable
sealed class Screen{
    @Serializable
    object FirstOpened: Screen()
    @Serializable
    object AuthScreen: Screen()
    @Serializable
    object MainScreen: Screen()
}