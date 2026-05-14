package com.example.domain.settings.authorization

import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor(val settings: ISettingsRepository) {
    suspend operator fun invoke(): Boolean {
       return when(settings.userIdFlow.first()){
            null -> false
            else -> true
        }
    }
}
