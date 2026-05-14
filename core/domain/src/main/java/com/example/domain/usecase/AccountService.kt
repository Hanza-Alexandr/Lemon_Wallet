package com.example.domain.usecase

import com.example.domain.settings.authorization.ISettingsRepository
import com.example.domain.state.AuthorizationState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountService@Inject constructor(private val dataStorePreferences: ISettingsRepository) {
    val stateAuth = dataStorePreferences.userIdFlow
        .map {
            when (it) {
                null -> AuthorizationState.NoAuthorization
                -1 -> AuthorizationState.Guest
                else -> AuthorizationState.Authorization(it)
            }
        }
}