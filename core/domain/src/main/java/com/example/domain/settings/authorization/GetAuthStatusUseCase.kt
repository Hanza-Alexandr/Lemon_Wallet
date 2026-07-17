package com.example.domain.settings.authorization

import com.example.domain.settings.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor(val settings: ISettingsRepository) {
    val status:Flow<Boolean> = settings.userIdFlow.map { userId ->
        userId != null
    }
    //Устарел
    suspend operator fun invoke(): Boolean {
       return when(settings.userIdFlow.first()){
            null -> false
            else -> true
        }
    }
}
