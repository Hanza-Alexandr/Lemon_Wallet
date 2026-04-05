package com.example.lemonwallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lemonwallet.model.repository.LocalDataStoreRepository
import com.example.lemonwallet.model.repository.StorageLocalRepository
import com.example.lemonwallet.model.repository.StorageRoomRepository
import com.example.lemonwallet.model.roomdb.dao.StorageDao
import com.example.lemonwallet.model.roomdb.database.AppDatabase
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.ui.view.navigation.AppNavigation
import com.example.lemonwallet.ui.theme.LemonWalletTheme
import com.example.lemonwallet.viewmodel.AuthState
import com.example.lemonwallet.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val storageRepo by lazy {
        AppDatabase.init(applicationContext)
        val db = AppDatabase.appDatabase
        StorageRoomRepository(db.getStorageDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {




        val repoDatastore = LocalDataStoreRepository(this)
        val storageRepo =storageRepo
        val storageSer = StorageService(storageRepo)
        val vm = MainViewModel(repoDatastore, storageSer )
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            val isLoadingAuth = vm.stateAuth.value is AuthState.Loading
            val isLoadingIsFirstOpeningApp = vm.isFirstOpeningApp.value == null
            isLoadingAuth || isLoadingIsFirstOpeningApp
        }

        enableEdgeToEdge()
        setContent {
            LemonWalletTheme {
                AppNavigation(vm)
            }
        }
    }
}