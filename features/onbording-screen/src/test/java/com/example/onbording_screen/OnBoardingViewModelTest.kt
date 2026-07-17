package com.example.onbording_screen

import com.example.domain.settings.onboarding.OnBoardingStatusUseCase
import com.example.navigation.INavigator
import com.example.onbording_screen.ui.OnBoardingViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OnBoardingViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var VM: OnBoardingViewModel
    private val mockKNavigator = mockk<INavigator>(relaxed = true)
    private val mockKUseCase = mockk<OnBoardingStatusUseCase>(relaxed = true)

    @Before
    fun setUp(){
        VM = OnBoardingViewModel(mockKNavigator, mockKUseCase)
    }
    @Test
    fun test1_onBoardingViewModelTest(){
        coEvery { mockKUseCase.setFalseStatus() } returns Unit

        VM.onFinished()

        coVerify(exactly = 1) {mockKUseCase.setFalseStatus() }
        verify(exactly = 1) {  mockKNavigator.navigateTo(any())

        }
    }

}