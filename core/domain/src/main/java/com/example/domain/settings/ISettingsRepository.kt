package com.example.domain.settings

import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    val userIdFlow: Flow<Int?>
    val isFirstOpeningApp: Flow<Boolean>
    val indexesSelectedStorageFlow: Flow<Set<Int>>
    suspend fun logIn(id: Int)
    suspend fun loginAsGuest()
    suspend fun logOut()
    suspend fun markFirstAppOpeningCompleted()
    suspend fun saveSelectedIds(indexes: Set<Int>)
}