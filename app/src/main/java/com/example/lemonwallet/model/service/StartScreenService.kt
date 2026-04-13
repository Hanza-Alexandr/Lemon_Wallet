package com.example.lemonwallet.model.service

import com.example.lemonwallet.model.repository.PreferencesDataStore
import com.example.lemonwallet.model.state.AuthState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StartScreenService @Inject constructor(private val dataStorePreferences: PreferencesDataStore){
    val stateAuth = dataStorePreferences.Account().userIdFlow
        .map {
            when (it) {
                null -> AuthState.NoAuth
                -1 -> AuthState.Guest
                else -> AuthState.Auth(it)
            }
        }


    val isFirstOpeningApp = dataStorePreferences.FirstOpen().isFirstOpeningApp


    suspend fun markFirstAppOpeningCompleted(){
        dataStorePreferences.FirstOpen().markFirstAppOpeningCompleted()
    }

    suspend fun logIn(id: Int){
        dataStorePreferences.Account().logIn(id)

    }

}