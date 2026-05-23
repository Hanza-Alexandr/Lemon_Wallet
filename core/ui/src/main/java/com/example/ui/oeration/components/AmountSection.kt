package com.example.ui.oeration.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
private fun AmountSectionPreview() {
    AmountSection("5000+123-(123-123)","5 123")
}
@Composable
fun AmountSection(
    expression: String ,
    result: String ,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.End // Выравнивание всего блока по правому краю
    ) {
        // Главный результат с символом "="
        Text(
            text = "=$result",
            style = MaterialTheme.typography.displayMedium.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF1D2126) // Темный цвет текста
            )
        )

        // Математическое выражение под результатом
        Text(
            text = expression,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 32.sp,
                color = Color(0xFFB0B9C5) // Светло-серый цвет для формулы
            )
        )
    }
}