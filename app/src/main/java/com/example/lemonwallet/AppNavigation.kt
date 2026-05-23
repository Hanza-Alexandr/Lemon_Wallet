package com.example.lemonwallet

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.example.storage_block.ui.components.blocks.components.lastoperations.LastOperationBlock
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
                    LastOperationBlock()
                }
            )
        )
    }
    composable<NavigationRoute.EditStorage>(
        enterTransition = {
            // Экран выезжает справа налево и проявляется (Fade)
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            // При переходе дальше (глубже) экран уходит влево
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        },
        popEnterTransition = {
            // При возврате назад этот экран въезжает слева
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExitTransition = {
            // При нажатии "Назад" экран уезжает вправо
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        }
    ){
        EditStorageScreen()
    }
    composable<NavigationRoute.CreateStorage>(
        enterTransition = {
            // Экран выезжает справа налево и проявляется (Fade)
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            // При переходе дальше (глубже) экран уходит влево
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        },
        popEnterTransition = {
            // При возврате назад этот экран въезжает слева
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExitTransition = {
            // При нажатии "Назад" экран уезжает вправо
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        }
    ){
        CreateStorageScreen()
    }
}