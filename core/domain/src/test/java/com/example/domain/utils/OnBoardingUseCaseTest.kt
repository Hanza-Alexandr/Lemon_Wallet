package com.example.domain.utils

import com.example.domain.settings.ISettingsRepository
import com.example.domain.settings.onboarding.OnBoardingStatusUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class OnBoardingUseCaseTest {
    private val mockKRepository = mockk<ISettingsRepository>(relaxed = true)
    private lateinit var useCase: OnBoardingStatusUseCase

    @BeforeEach
    fun setUp(){
        useCase = OnBoardingStatusUseCase(mockKRepository)
    }

    @Test
    fun `get status if first open`() = runTest{
        //Задаю поведение метода который будет в процессе дергаться
        coEvery { mockKRepository.isFirstOpeningApp } returns flowOf(true)
        //Сохраняю результат метода
        val actual = useCase.getStatus()
        //Проверяю что метод дернулся и ровно один раз
        coVerify(exactly = 1) {mockKRepository.isFirstOpeningApp}
        //Проверяю что что метод вернул то что должен был
        assertEquals(true, actual)
    }

    @Test
    fun `set false status if first open`() = runTest {
        coEvery { mockKRepository.isFirstOpeningApp } returns flowOf(true)
        useCase.setFalseStatus()
        coVerify(exactly = 1) {mockKRepository.markFirstAppOpeningCompleted()}
    }

    @Test
    fun `nothing if status not first open`() = runTest {
        coEvery { mockKRepository.isFirstOpeningApp } returns flowOf(false)
        useCase.setFalseStatus()
        coVerify(exactly = 0) { mockKRepository.markFirstAppOpeningCompleted() }
    }
}