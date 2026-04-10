package com.example.lemonwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

class MainActivity : ComponentActivity() {

    private val storageRepo by lazy {
        AppDatabase.init(applicationContext)
        val db = AppDatabase.appDatabase
        StorageRoomRepository(db.getStorageDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        val repoDatastore = PreferencesDataStore(this)

        val storageRepo =storageRepo

        val storageSer = StorageService(storageRepo, repoDatastore)
        val startScreenServ = StartScreenService(repoDatastore)
        val startVm = StartScreenViewModel(startScreenServ)
        val vm = MainViewModel(storageSer )

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            val isLoadingAuth = startVm.stateAuth.value is AuthState.Loading
            val isLoadingStorage = vm.storageList.value == null
            val isLoadingIsFirstOpeningApp = startVm.isFirstOpeningApp.value == null
            isLoadingAuth || isLoadingIsFirstOpeningApp || isLoadingStorage
        }

        enableEdgeToEdge()
        setContent {
            LemonWalletTheme {
                AppNavigation(startVm,vm)
            }
        }
    }
}