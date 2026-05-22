package com.example.domain.usecase

import com.example.domain.settings.ISettingsRepository
import com.example.domain.state.AuthorizationState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccountService@Inject constructor(private val dataStorePreferences: ISettingsRepository) {

    val stateAuth = dataStorePreferences.userIdFlow
        .map {
            when (it) {
                null -> AuthorizationState.NoAuthorization
                else -> dataStorePreferences.userIdFlow.map {
                    AuthorizationState.Authorization(it?: throw Exception("userId is null"))
                }
            }
        }
}