package com.example.lemonwallet.ui.view.templatestorage.colorpickerrow

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lemonwallet.model.domain.NewColor
import com.example.lemonwallet.ui.state.ColorUIState
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun AddColorDialogPreview() {
    AddColorDialog(onDismiss = {}, onColorConfirmed = {}, availableColors = listOf())
}

@Composable
fun AddColorDialog(
    availableColors: List<ColorUIState>,
    onDismiss: () -> Unit,
    onColorConfirmed: (NewColor) -> Unit
) {
    var hue by remember { mutableFloatStateOf(0f) }
    var alpha by remember { mutableFloatStateOf(1f) }
    val saturation = 0.8f
    val brightness = 0.9f

    val currentColor = remember(hue, alpha) {
        Color.hsv(hue, saturation, brightness).copy(alpha = alpha)
    }

    // HEX строка (ARGB), которая синхронизируется с currentColor
    var hexInput by remember(currentColor) {
        mutableStateOf(String.format("#%08X", currentColor.toArgb()))
    }

    // Проверка: существует ли такой цвет уже в списке (сравниваем через ARGB Int)
    val isColorAlreadyExists = remember(currentColor, availableColors) {
        availableColors.any { it.toColor().toArgb() == currentColor.toArgb() }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Выберите цвет", style = MaterialTheme.typography.headlineSmall)

                // Контейнер для круга и превью в центре
                Box(contentAlignment = Alignment.Center) {
                    HueWheelPicker(
                        hue = hue,
                        modifier = Modifier.size(200.dp),
                        onHueChanged = { hue = it }
                    )

                    // Превью выбранного цвета в центре круга
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(currentColor)
                    )
                }

                // Ползунок прозрачности
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Прозрачность",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Slider(
                        value = alpha,
                        onValueChange = { alpha = it },
                        valueRange = 0f..1f
                    )
                }

                // Поле ввода HEX (ARGB)
                OutlinedTextField(
                    value = hexInput,
                    onValueChange = { newValue ->
                        hexInput = newValue
                        try {
                            val cleanHex = if (newValue.startsWith("#")) newValue else "#$newValue"
                            if (cleanHex.length == 7 || cleanHex.length == 9) {
                                val colorInt = android.graphics.Color.parseColor(cleanHex)
                                val hsv = FloatArray(3)
                                android.graphics.Color.colorToHSV(colorInt, hsv)
                                hue = hsv[0]
                                alpha = android.graphics.Color.alpha(colorInt) / 255f
                            }
                        } catch (_: Exception) {}
                    },
                    label = { Text("HEX код (ARGB)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = isColorAlreadyExists,
                    supportingText = {
                        if (isColorAlreadyExists) {
                            Text(
                                text = "Этот цвет уже добавлен",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) { Text("Отмена") }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        enabled = !isColorAlreadyExists,
                        onClick = { onColorConfirmed(NewColor(hexInput)) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Добавить")
                    }
                }
            }
        }
    }
}

@Composable
fun HueWheelPicker(
    hue: Float,
    modifier: Modifier = Modifier,
    onHueChanged: (Float) -> Unit
) {
    Canvas(modifier = modifier
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                val center = Offset(size.width / 2f, size.height / 2f)
                val pos = offset - center
                var angle = Math.toDegrees(atan2(pos.y.toDouble(), pos.x.toDouble())).toFloat()
                if (angle < 0) angle += 360f
                onHueChanged(angle)
            }
        }
        .pointerInput(Unit) {
            detectDragGestures { change, _ ->
                val center = Offset(size.width / 2f, size.height / 2f)
                val pos = change.position - center
                var angle = Math.toDegrees(atan2(pos.y.toDouble(), pos.x.toDouble())).toFloat()
                if (angle < 0) angle += 360f
                onHueChanged(angle)
            }
        }
    ) {
        val strokeWidth = 30f
        val center = Offset(size.width / 2f, size.height / 2f)
        val radius = (size.minDimension - strokeWidth) / 2f
        val colors = List(360) { Color.hsv(it.toFloat(), 1f, 1f) }

        // Рисуем цветовое кольцо
        drawArc(
            brush = Brush.sweepGradient(colors, center),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth)
        )

        // Рисуем точку-индикатор (ползунок)
        val angleRad = Math.toRadians(hue.toDouble())
        val x = center.x + radius * cos(angleRad).toFloat()
        val y = center.y + radius * sin(angleRad).toFloat()

        // Темная точка без обводки
        drawCircle(
            color = Color(0xFF1C1B1F),
            radius = 10.dp.toPx(),
            center = Offset(x, y)
        )
    }
}
