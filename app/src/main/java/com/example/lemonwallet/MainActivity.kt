package com.example.lemonwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.lemonwallet.model.state.DataLoadingState
import com.example.lemonwallet.ui.view.navigation.AppNavigation
import com.example.lemonwallet.ui.theme.LemonWalletTheme
import com.example.lemonwallet.viewmodel.MainScreenViewModel
import com.example.lemonwallet.viewmodel.StartScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val startScreenViewModel: StartScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            !startScreenViewModel.dataIsReady.value
        }

        enableEdgeToEdge()
        setContent {
            LemonWalletTheme {
                AppNavigation()
            }
        }
    }
}
