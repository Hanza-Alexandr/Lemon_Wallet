package com.example.domain.settings.authorization

import com.example.domain.IUserSettingsRepository
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor() {
    suspend operator fun invoke(): Boolean {
        return false
    }
}
