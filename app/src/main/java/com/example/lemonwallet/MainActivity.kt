package com.example.lemonwallet

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.lemonwallet.ui.theme.LemonWalletTheme
import com.example.navigation.INavigator
import com.example.navigation.NavigationAction
import com.example.navigation.NavigationRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
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
            LaunchedEffect(Unit) {
                val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
                    .build()
                WorkManager.getInstance(applicationContext).enqueue(workRequest)
            }

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

                var startScreen = getStartScreen(onBoardingStatus as Boolean, authStatus as Boolean)
                Log.i("STARTSCREEN", "Screen = ${startScreen.toString()} onBoarding = ${onBoardingStatus} auth = ${authStatus}")
                LemonWalletTheme {
                    AppNavigation(navController, startScreen)
                }
            }



        }
    }
}

fun getStartScreen(onBoardingStatus: Boolean, authStatus: Boolean): NavigationRoute {
    return if (authStatus) {
        NavigationRoute.MainScreen
    } else {
        if (onBoardingStatus) NavigationRoute.OnBoarding
        else NavigationRoute.AuthorizationScreen
    }
}



class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params){
    override suspend fun doWork(): Result {
        Log.d("WorkManagerTest", "🚀 Воркер запущен! Имитируем загрузку данных...")
        return try {
            delay(5000)

            Log.d("WorkManagerTest", "✅ Работа завершена успешно!")
            Result.success()
        } catch (ex: Exception) {
            Log.e("WorkManagerTest", "❌ Ошибка в воркере", ex)
            Result.retry()
        }
    }
    fun foo(): Int{
        return 2
    }
}