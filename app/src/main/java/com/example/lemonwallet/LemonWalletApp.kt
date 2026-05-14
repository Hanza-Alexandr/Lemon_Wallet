package com.example.lemonwallet

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LemonWalletApp : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}