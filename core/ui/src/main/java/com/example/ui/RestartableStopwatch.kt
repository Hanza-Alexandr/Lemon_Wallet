package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun RestartableStopwatch(
    resetKey: Any? // Ключ перезапуска (наш список)
) {
    var secondsPassed by remember { mutableIntStateOf(0) }


    // Перезапускается каждый раз при изменении resetKey
    LaunchedEffect(resetKey) {
        secondsPassed = 0 // Сбрасываем секундомер в 0

        while (isActive) {
            delay(1000L) // Ждем 1 секунду
            secondsPassed++
        }
    }

    Text(
        text = "Прошло времени: $secondsPassed сек.",
        modifier = Modifier.padding(8.dp)
    )
}