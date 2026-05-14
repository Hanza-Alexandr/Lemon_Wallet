package com.example.domain.settings.onboarding

import com.example.domain.settings.ISettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class OnBoardingStatusInteractor @Inject constructor(private val settings: ISettingsRepository) {
    suspend fun getStatus(): Boolean{

        return when(settings.isFirstOpeningApp.first()){
            false -> false
            true -> true
        }
    }
    suspend fun setFalseStatus(){
        if (settings.isFirstOpeningApp.first()){
            settings.markFirstAppOpeningCompleted()
        }
    }
}
