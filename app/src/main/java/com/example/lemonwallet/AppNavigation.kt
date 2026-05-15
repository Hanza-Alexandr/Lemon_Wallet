package com.example.lemonwallet

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.auth.ui.AuthorizationScreen
import com.example.main_screen.ui.MainScreen
import com.example.navigation.NavigationRoute
import com.example.onbording_screen.ui.OnBoardingScreen
import com.example.storage_block.ui.StorageBlock
import com.example.ui.storage.createstorage.CreateStorageScreen
import com.example.ui.storage.editstorage.EditStorageScreen


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
        MainScreen(
            blockList = listOf(
                {
                    StorageBlock()
                }
            )
        )
    }
    composable<NavigationRoute.EditStorage>{
        EditStorageScreen()
    }
    composable<NavigationRoute.CreateStorage>{
        CreateStorageScreen()
    }
}