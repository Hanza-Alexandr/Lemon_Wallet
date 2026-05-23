package com.example.domain.settings

import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    val userIdFlow: Flow<String>
    val isFirstOpeningApp: Flow<Boolean>
    val idSelectedStorageFlow: Flow<Set<String>>
    suspend fun logIn(id: String)
    suspend fun loginAsGuest()
    suspend fun logOut()
    suspend fun markFirstAppOpeningCompleted()
    suspend fun saveSelectedIds(id: Set<String>)
}