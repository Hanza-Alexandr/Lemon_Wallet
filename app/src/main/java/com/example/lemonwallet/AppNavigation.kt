package com.example.lemonwallet

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.example.player_screen.ui.PlayerScreen
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

        enterTransition = {
            // Устанавливаем скорость в 500 миллисекунд
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(500))
        },
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
    composable<NavigationRoute.BleScreen> {
        BleScreen()
    }
    composable<NavigationRoute.PlayerScreen> {
        PlayerScreen()
    }
}