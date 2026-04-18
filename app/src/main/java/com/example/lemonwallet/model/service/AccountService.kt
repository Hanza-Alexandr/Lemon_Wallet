package com.example.lemonwallet.model.service

import com.example.lemonwallet.model.repository.PreferencesDataStore
import com.example.lemonwallet.model.state.AuthorizationState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountService@Inject constructor(private val dataStorePreferences: PreferencesDataStore) {
    val stateAuth = dataStorePreferences.userIdFlow
        .map {
            when (it) {
                null -> AuthorizationState.NoAuthorization
                -1 -> AuthorizationState.Guest
                else -> AuthorizationState.Authorization(it)
            }
        }
}