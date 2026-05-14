package com.example.lemonwallet

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.auth.ui.AuthorizationScreen
import com.example.main_screen.ui.MainScreen
import com.example.navigation.ManagerScreens
import com.example.onbording_screen.ui.StartScreen
import com.example.ui.storage.CreateStorageScreen
import com.example.ui.storage.EditStorageScreen


@Composable
fun AppNavigation(
    startManagerScreens: ManagerScreens
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startManagerScreens,
    ){
        this.navigationManager()
    }

}

fun NavGraphBuilder.navigationManager (){
    composable<ManagerScreens.OnBoarding> {
        StartScreen()
    }
    composable<ManagerScreens.AuthorizationScreen> {
        AuthorizationScreen()
    }
    composable<ManagerScreens.MainManagerScreens>{
        MainScreen()
    }
    composable<ManagerScreens.EditStorage>{
        EditStorageScreen()
    }
    composable<ManagerScreens.CreateStorage>{
        CreateStorageScreen()
    }
}