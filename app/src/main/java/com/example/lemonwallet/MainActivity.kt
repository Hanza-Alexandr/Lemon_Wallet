package com.example.lemonwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.repository.PreferencesDataStore
import com.example.lemonwallet.model.repository.StorageRoomRepository
import com.example.lemonwallet.model.roomdb.database.AppDatabase
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.ui.view.navigation.AppNavigation
import com.example.lemonwallet.ui.theme.LemonWalletTheme
import com.example.lemonwallet.model.state.AuthState
import com.example.lemonwallet.viewmodel.MainViewModel
import com.example.lemonwallet.model.service.StartScreenService
import com.example.lemonwallet.viewmodel.StartScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonWalletTheme {
                AppNavigation()
            }
        }
    }
}

