package com.example.lemonwallet

import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.api_screen.ApiScreen
import com.example.auth.ui.AuthorizationScreen
import com.example.ble_screen.ui.BleScreen
import com.example.categoryselection_screen.ui.CategorySelectScreen
import com.example.detailoperation_screen.ui.create.CreateOperationScreen
import com.example.detailoperation_screen.ui.edit.EditOperationScreen
import com.example.detailstorage_screen.ui.create.CreateStorageScreen
import com.example.detailstorage_screen.ui.edit.EditStorageScreen
import com.example.main_screen.ui.MainScreen
import com.example.navigation.NavigationRoute
import com.example.onbording_screen.ui.OnBoardingScreen
import com.example.storage_block.ui.StorageBlock
import com.example.storage_block.ui.components.blocks.components.lastoperations.LastOperationBlock
import com.example.ui.camera.CameraPreviewScreen
import com.example.ui.multimenu.MultiMenuBlock


@Composable
fun AppNavigation(
    navController: NavHostController,
    startNavigationRoute: NavigationRoute
){

    NavHost(
        navController = navController,
        startDestination = startNavigationRoute,

        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
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
                    LastOperationBlock()
                    MultiMenuBlock()

                }
            )
        )
    }
    composable<NavigationRoute.EditStorage> {
        EditStorageScreen()
    }
    composable<NavigationRoute.CreateStorage>{
        CreateStorageScreen()
    }
    composable<NavigationRoute.CreateOperation>{
        CreateOperationScreen()
    }

    composable<NavigationRoute.EditOperation>{
        EditOperationScreen()
    }
    composable<NavigationRoute.CategorySelectScreen>{
        CategorySelectScreen()
    }
    composable<NavigationRoute.CameraPreviewScreen>{
        CameraPreviewScreen()
    }
    composable<NavigationRoute.ApiScreen> {
        ApiScreen()
    }
}