package com.example.lemonwallet.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.lemonwallet.ui.features.authorization.AuthorizationScreen
import com.example.lemonwallet.ui.features.mainscreen.MainScreenView
import com.example.lemonwallet.ui.features.onboarding.StartScreenView
import com.example.domain.state.AuthorizationState
import com.example.lemonwallet.ui.common.storage.manage.create.CreateStorageScreen
import com.example.lemonwallet.ui.common.storage.manage.edit.EditStorageScreen
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

    val startScreen = when {
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
                startScreenVM.markFirstAppOpeningCompleted()
                navController.navigate(Screen.AuthScreen) {
                    popUpTo(Screen.FirstOpened) { inclusive = true }
                }
            }
        }
        composable<Screen.AuthScreen> {
            AuthorizationScreen(
                onLoginSuccess = {},
                onContinueAsGuest = {
                    startScreenVM.logIn(-1)
                    navController.navigate(Screen.MainScreen) {
                        popUpTo(Screen.AuthScreen) { inclusive = true }
                    }
                }
            )
        }
        composable<Screen.MainScreen>{
            MainScreenView(
                onEditStorageClick = { id ->
                    navController.navigate(Screen.EditStorage(storageId = id))
                },
                onCreateStorageClick = {
                    navController.navigate(Screen.CreateStorage)
                }
            )
        }
        composable<Screen.EditStorage>{ backStackEntry ->
            val route: Screen.EditStorage = backStackEntry.toRoute()
            EditStorageScreen(
                storageId = route.storageId,
                onBack = { navController.popBackStack() }
            )
        }

        composable<Screen.CreateStorage>{
            CreateStorageScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }

}

@Serializable
sealed class Screen {
    @Serializable
    data object FirstOpened: Screen()
    @Serializable
    data object AuthScreen: Screen()
    @Serializable
    data object MainScreen: Screen()
    @Serializable
    data class EditStorage(val storageId: Long): Screen()
    @Serializable
    object CreateStorage: Screen()
}
