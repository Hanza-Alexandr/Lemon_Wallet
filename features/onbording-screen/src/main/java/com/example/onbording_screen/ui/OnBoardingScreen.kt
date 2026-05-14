package com.example.onbording_screen.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.onbording_screen.model.PreviewPageUiState
import com.example.onbording_screen.ui.components.PagePart
import com.example.onbording_screen.ui.components.SkipButton

@Preview
@Composable
fun OnBoardingScreen(onBoardingViewModel: OnBoardingViewModel = hiltViewModel()){
    OnBoardingScreenContent(
        onFinished = onBoardingViewModel::onFinished
    )
}
@Composable
fun OnBoardingScreenContent(onFinished: () -> Unit){
    // Определяем страницы
    val pages = listOf(
        PreviewPageUiState(
            title = "Приветствую в Lemon Wallet",
            description = "Записывай свой операции, смотри куда ушли деньги и корректируй свою фин. стратегию",
            backgroundColor = Color(0xFFFEFCE8), // Light Yellow
            mainColor = Color(0xFFEAB308),       // Yellow 600
            emoji = "🍋"
        ),
        PreviewPageUiState(
            title = "Открыл, записал и забыл",
            description = "Максимально быстро записывай свои операции, что бы это вошло в привычку",
            backgroundColor = Color(0xFFF7FEE7), // Light Lime
            mainColor = Color(0xFF84CC16),       // Lime 600
            emoji = "💾"
        ),
        PreviewPageUiState(
            title = "Твоя надежная статистика",
            description = "Отчеты, графики и всякие разные цифирки. Все что бы ты смог принимать решения качественее.",
            backgroundColor = Color(0xFFF0FDF4), // Light Green
            mainColor = Color(0xFF22C55E),       // Green 600
            emoji = "📊"
        )
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    // Анимируем цвет фона при переключении страниц
    val animatedBackgroundColor by animateColorAsState(
        targetValue = pages[pagerState.currentPage].backgroundColor,
        label = "BackgroundAnimation"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedBackgroundColor)
    ) {
        // Кнопка "Пропустить" поверх всего
        SkipButton(pages, pagerState, scope, onFinished)
        PagePart(pages, pagerState, scope, onFinished)
    }
}

// Данные для каждого экрана приветствия


