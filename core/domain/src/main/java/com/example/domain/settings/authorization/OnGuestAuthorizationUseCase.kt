package com.example.domain.settings.authorization

import com.example.domain.settings.ISettingsRepository
import javax.inject.Inject

class OnGuestAuthorizationUseCase @Inject constructor(val settings: ISettingsRepository){
    suspend operator fun invoke(){
        settings.loginAsGuest()
    }
}