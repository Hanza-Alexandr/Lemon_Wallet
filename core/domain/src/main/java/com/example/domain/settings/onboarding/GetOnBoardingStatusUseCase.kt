package com.example.domain.settings.onboarding

import com.example.domain.settings.authorization.ISettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetOnBoardingStatusUseCase @Inject constructor(val settings: ISettingsRepository) {
    suspend operator fun invoke(): Boolean{

        return when(settings.isFirstOpeningApp.first()){
            false -> false
            true -> true
        }
    }
}
