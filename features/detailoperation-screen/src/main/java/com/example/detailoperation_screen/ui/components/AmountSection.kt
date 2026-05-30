package com.example.detailoperation_screen.ui.components

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
import com.example.domain.utils.toFormatAmount
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Preview
@Composable
private fun AmountSectionPreview() {
    AmountSection("5000+123-(123-123)",512300)
}
@Composable
fun AmountSection(
    expression: String,
    result: Long,
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
            text = "=${result.toFormatAmount()}",
            style = MaterialTheme.typography.displayMedium.copy(
                fontSize = 48.sp,
                fontWeight = FontWeight.W500,
                color = Color(0xFF1D2126) // Темный цвет текста
            )
        )

        // Математическое выражение под результатом
        Text(
            text = expression.let { it.ifEmpty { "0" } }.formatAmount(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 32.sp,
                color = Color(0xFFB0B9C5) // Светло-серый цвет для формулы
            )
        )
    }
}

fun String.formatAmount(): String {
    if (this.isEmpty() || this == "0") return "0"

    return try {
        val symbols = DecimalFormatSymbols().apply {
            groupingSeparator = ' ' // Разделитель тысяч — пробел
            decimalSeparator = ','  // Разделитель копеек — запятая
        }
        // Шаблон: разделять тысячи, выводить до 2 знаков после запятой, если они есть
        val formatter = DecimalFormat("#,##0.##", symbols)
        val number = this.replace(",", ".").toDouble()
        formatter.format(number)
    } catch (e: Exception) {
        this // Если это выражение (с плюсами и минусами), возвращаем как есть
    }
}

