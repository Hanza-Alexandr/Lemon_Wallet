package com.example.lemonwallet

import com.example.domain.settings.authorization.GetAuthStatusUseCase
import com.example.domain.settings.onboarding.OnBoardingStatusUseCase
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class) // Подключаем диспетчер здесь
class MainViewModelTest {

    private val onboardingUseCase = mockk<OnBoardingStatusUseCase>()
    private val authUseCase = mockk<GetAuthStatusUseCase>()

    private lateinit var VM: MainViewModel

    @BeforeEach
    fun setUp(){
        VM = MainViewModel(onboardingUseCase, authUseCase)
    }

}