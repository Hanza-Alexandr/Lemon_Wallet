package com.example.domain.settings.onboarding

import com.example.domain.settings.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class OnBoardingStatusUseCase @Inject constructor(private val settings: ISettingsRepository) {

    val status: Flow<Boolean> = settings.isFirstOpeningApp

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
