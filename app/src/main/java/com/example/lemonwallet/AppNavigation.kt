package com.example.lemonwallet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.auth.ui.AuthorizationScreen
import com.example.main_screen.ui.MainScreen
import com.example.navigation.NavigationRoute
import com.example.onbording_screen.ui.OnBoardingScreen
import com.example.ui.storage.CreateStorageScreen
import com.example.ui.storage.EditStorageScreen
import javax.inject.Inject


@Composable
fun AppNavigation(
    navController: NavHostController,
    startNavigationRoute: NavigationRoute
){

    NavHost(
        navController = navController,
        startDestination = startNavigationRoute,
    ){
        this.navigationManager()
    }
}

fun NavGraphBuilder.navigationManager (){
    composable<NavigationRoute.OnBoarding> {
        OnBoardingScreen()
    }
    composable<NavigationRoute.AuthorizationScreen> {
        AuthorizationScreen()
    }
    composable<NavigationRoute.MainScreen>{
        MainScreen()
    }
    composable<NavigationRoute.EditStorage>{
        EditStorageScreen()
    }
    composable<NavigationRoute.CreateStorage>{
        CreateStorageScreen()
    }
}