package com.example.detailoperation_screen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorKeyboard(
    modifier: Modifier = Modifier,
    onKeyClick: (String) -> Unit
) {
    // Определяем структуру кнопок
    val keys = listOf(
        listOf("AC", "( )", "%", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "−"),
        listOf("1", "2", "3", "+"),
        listOf("0", ".", "⌫", "=")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F2)) // Светло-серый фон всей клавиатуры (опционально)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { key ->
                    CalcButton(
                        text = key,
                        modifier = Modifier.weight(1f),
                        onClick = { onKeyClick(key) }
                    )
                }
            }
        }
    }
}

@Composable
fun CalcButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Определяем стиль кнопки на основе текста
    val isOperation = text in listOf("÷", "×", "−", "+", "=", "AC", "( )", "%", "⌫")
    val backgroundColor = if (isOperation) Color(0xFFE0E0E0) else Color(0xFFEBEBEB)
    val contentColor = Color(0xFF1D2126)

    Box(
        modifier = modifier
            .aspectRatio(1.5f) // Делаем кнопки вытянутыми (капсулы)
            .clip(RoundedCornerShape(32.dp)) // Скругление как на макете
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = contentColor
            )
        )
    }
}

