package com.example.ble_screen.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.isActive

@Composable
fun rememberFps(): Int {
    var fps by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        var frameCount = 0
        var lastTimeNanos = System.nanoTime()

        while (isActive) {
            withFrameNanos { currentFrameNanos ->
                frameCount++
                val deltaNanos = currentFrameNanos - lastTimeNanos
                if (deltaNanos >= 1_000_000_000L) { // Каждую секунду
                    fps = ((frameCount * 1_000_000_000L) / deltaNanos).toInt()
                    frameCount = 0
                    lastTimeNanos = currentFrameNanos
                }
            }
        }
    }

    return fps
}

@Composable
fun PerformanceTestAnimation(
    modifier: Modifier = Modifier
) {
    val fps = rememberFps()
    val infiniteTransition = rememberInfiniteTransition(label = "PerformanceTransition")

    // 1. Бесконечное вращение (0°..360° за 1 секунду)
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RotationAnimation"
    )

    // 2. Бегущий шарик влево-вправо (0%..100% за 1.5 секунды)
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ScannerAnimation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.9f))
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Вращающийся индикатор (если есть лаг, вращение становится прерывистым)
            Canvas(
                modifier = Modifier
                    .size(28.dp)
                    .rotate(rotation)
            ) {
                drawArc(
                    color = Color(0xFF6200EE),
                    startAngle = 0f,
                    sweepAngle = 270f,
                    useCenter = false,
                    style = Stroke(width = 4.dp.toPx())
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Тест плавности",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            // Счетчик FPS с подсвечивающимся цветом
            val fpsColor = when {
                fps >= 55 -> Color(0xFF2E7D32) // Зеленый (Отлично)
                fps >= 30 -> Color(0xFFEF6C00) // Оранжевый (Небольшие просадки)
                else -> Color(0xFFC62828)      // Красный (Лаги)
            }

            Text(
                text = "$fps FPS",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = fpsColor
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Бегущая полоса (при поддергивании шарика виден пропуск кадров)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color.LightGray)
        ) {
            Canvas(
                modifier = Modifier.fillMaxWidth()
            ) {
                val ballRadius = 8.dp.toPx()
                val availableWidth = size.width - ballRadius * 2
                val currentX = ballRadius + availableWidth * progress

                drawCircle(
                    color = Color(0xFF6200EE),
                    radius = ballRadius,
                    center = androidx.compose.ui.geometry.Offset(currentX, size.height / 2)
                )
            }
        }
    }
}
