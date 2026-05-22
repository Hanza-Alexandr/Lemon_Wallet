package com.example.domain.usecase

import com.example.domain.settings.ISettingsRepository
import javax.inject.Inject

/**
 * Класс бизнес логики для старта приложения.
 */

class StartScreenService @Inject constructor(private val dataStorePreferences: ISettingsRepository, private val accountService: AccountService){
    val stateAuth = accountService.stateAuth

    val isFirstOpeningApp = dataStorePreferences.isFirstOpeningApp

    suspend fun markFirstAppOpeningCompleted(){
        dataStorePreferences.markFirstAppOpeningCompleted()
    }

    suspend fun logIn(id: String){
        dataStorePreferences.logIn(id)

    }

}