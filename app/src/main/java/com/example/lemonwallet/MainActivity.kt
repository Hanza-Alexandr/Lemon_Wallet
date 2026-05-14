package com.example.lemonwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import com.example.lemonwallet.ui.theme.LemonWalletTheme
import com.example.navigation.INavigator
import com.example.navigation.NavigationAction
import com.example.navigation.NavigationRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    @Inject lateinit var navigator: INavigator

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onBoardingStatus.collectLatest {
                    splashScreen.setKeepOnScreenCondition {
                        it == null
                    }
                }
            }
        }

        setContent {
            val navController = rememberNavController()

            LaunchedEffect(navController) {
                navigator.navigationActions.collect { action ->
                    when (action) {
                        is NavigationAction.To -> {
                            navController.navigate(action.route) {
                                action.popUpTo?.let {
                                    popUpTo(it) { inclusive = action.inclusive }
                                }
                            }
                        }
                        is NavigationAction.Back -> navController.popBackStack()
                    }
                }
            }
            val onBoardingStatus by viewModel.onBoardingStatus.collectAsState()
            val authStatus by viewModel.onAuthStatus.collectAsState()

            if (onBoardingStatus !=null && authStatus != null){

                var startScreen = if (authStatus == true) {
                    NavigationRoute.MainScreen
                }
                else {
                    if (onBoardingStatus == true) NavigationRoute.OnBoarding
                    else NavigationRoute.AuthorizationScreen
                }
                LemonWalletTheme {
                    AppNavigation(navController, startScreen)
                }
            }



            // 1) лделается сплеш скрин как в примере проекта


            //2) из главной VM которая mainVM(типо того). Получаються необходимые данные для запуска приложения. Как минимум AuthState и isFirstOpeningApp

            //3) Узнаеться старт скрин и запускаеться AppNav - см пример проекта




            //Перейти на useCase в главной VM примера можно увидеть что нет блять никаких сервисов а только определенные фичи т.е usecase (см. MainViewModel в примере)

        }
    }
}
