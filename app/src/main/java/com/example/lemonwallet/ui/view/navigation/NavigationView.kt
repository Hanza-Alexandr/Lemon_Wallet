package com.example.lemonwallet.ui.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lemonwallet.ui.view.authscreen.AuthScreenView
import com.example.lemonwallet.ui.view.mainscreen.MainScreenView
import com.example.lemonwallet.ui.view.startscreen.StartScreenView
import com.example.lemonwallet.viewmodel.AuthState
import com.example.lemonwallet.viewmodel.MainViewModel


@Composable
fun AppNavigation(vm: MainViewModel){
    val navController = rememberNavController()

    val isFirstOpen by vm.isFirstOpeningApp.collectAsState()
    val authState by vm.stateAuth.collectAsState()

    // 1. Ждем, пока оба условия выйдут из состояния загрузки
    val isLoading = isFirstOpen == null || authState is AuthState.Loading

    if (isLoading) {
        // Пока данные грузятся, мы ничего не рисуем (Splash удерживает экран)
        return
    }

    // 2. Вычисляем стартовый экран только когда данные ТОЧНО готовы
    // Используем remember(isFirstOpen, authState), чтобы он пересчитался, если данные изменятся
    val startScreen = remember(isFirstOpen, authState) {
        when {
            isFirstOpen == true -> Screen.FirstOpened
            authState is AuthState.Auth || authState is AuthState.Guest -> Screen.MainScreen
            else -> Screen.AuthScreen
        }
    }

     //Вообще данные должны браться из локальной бд. Первое открытие - FirstScreen, Не первое открытие, но не авторизовался - окно авторизации, авторизован или как гость - сразу главное меню
    NavHost(
        navController = navController,
        startDestination = startScreen,
    ){
        composable<Screen.FirstOpened> {
            StartScreenView {
                vm.markFirstAppOpeningCompleted() //Что бы больше стартоывый экран не открывался
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
                vm.logIn(-1)
                navController.navigate(Screen.MainScreen) {
                    popUpTo(Screen.AuthScreen) { inclusive = true }
                }
            })
        }
        composable<Screen.MainScreen> {
            MainScreenView(vm)
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