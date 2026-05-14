package com.example.domain.usecase

import com.example.domain.IUserSettingsRepository
import com.example.domain.state.AuthorizationState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountService@Inject constructor(private val dataStorePreferences: IUserSettingsRepository) {
    val stateAuth = dataStorePreferences.userIdFlow
        .map {
            when (it) {
                null -> AuthorizationState.NoAuthorization
                -1 -> AuthorizationState.Guest
                else -> AuthorizationState.Authorization(it)
            }
        }
}