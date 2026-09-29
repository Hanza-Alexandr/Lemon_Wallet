package com.example.ui

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin


@Composable
fun InteractiveTimerView(
    modifier: Modifier = Modifier,
    initialProgress: Float = 0.41f, // 25 минут из 60 ≈ 0.41
    onProgressChange: (Float) -> Unit = {}
) {
    // Состояние прогресса внутри вью
    var progress by remember { mutableFloatStateOf(initialProgress) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    // 1. Получаем координаты касания относительно центра
                    val dragPoint = change.position
                    val center = Offset(size.width / 2f, size.height / 2f)

                    // 2. Вычисляем угол в радианах
                    val angleRad = atan2(
                        dragPoint.y - center.y,
                        dragPoint.x - center.x
                    )

                    // 3. Переводим в прогресс (от 0 до 1)
                    // Добавляем PI/2, чтобы 0 был наверху
                    var angleDeg = Math.toDegrees(angleRad.toDouble()).toFloat() + 90f
                    if (angleDeg < 0) angleDeg += 360f

                    progress = angleDeg / 360f
                    onProgressChange(progress)
                }
            }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            val canvasSize = size.minDimension
            val radius = canvasSize / 2
            val center = Offset(x = size.width / 2, y = size.height / 2)
            val strokeWidth = 10.dp.toPx()

            // 1. Фоновый круг
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.3f),
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
            )

            // 2. Активная дуга
            drawArc(
                color = Color(0xFF90CAF9),
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // 3. Рисуем цифры (0, 5, 10... 55)
            val paint = Paint().apply {
                color = android.graphics.Color.GRAY
                textSize = 14.sp.toPx()
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
            }

            for (i in 0 until 60 step 5) {
                val angleDeg = i * 6f - 90f
                val angleRad = Math.toRadians(angleDeg.toDouble())
                val labelRadius = radius + 25.dp.toPx()
                val x = center.x + labelRadius * cos(angleRad).toFloat()
                val y = center.y + labelRadius * sin(angleRad).toFloat()

                drawContext.canvas.nativeCanvas.drawText(
                    i.toString(),
                    x,
                    y + (paint.textSize / 3), // небольшая центровка по вертикали
                    paint
                )
            }

            // 4. Точка-индикатор (ползунок)
            val indicatorAngleRad = (360f * progress - 90f) * (PI.toFloat() / 180f)
            val indicatorX = center.x + radius * cos(indicatorAngleRad)
            val indicatorY = center.y + radius * sin(indicatorAngleRad)

            drawCircle(
                color = Color(0xFF90CAF9),
                radius = strokeWidth * 1.2f,
                center = Offset(indicatorX, indicatorY)
            )
        }

        // Центральный текст (вычисляем минуты)
        val minutes = (progress * 60).roundToInt()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = String.format("%02d:00", minutes),
                fontSize = 44.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF333333)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTimer() {
    InteractiveTimerView(modifier = Modifier.size(300.dp))
}