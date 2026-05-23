package com.example.domain.usecase

import com.example.domain.settings.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(private val settings: ISettingsRepository) {
    suspend fun getId(): String{
        return settings.userIdFlow.first()?: "GUEST"
    }
    fun getIfFLow(): Flow<String> {
        return settings.userIdFlow
    }
}