package com.example.domain.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Long.toFormatAmount(): String {
    val symbols = DecimalFormatSymbols().apply {
        groupingSeparator = ' ' // Разделитель тысяч
        decimalSeparator = ','  // Разделитель копеек
    }

    // Формат #,##0.00 заставляет всегда показывать две цифры копеек (банковский стандарт)
    // Если копейки не нужны, если они .00, используйте "#,##0.##"
    val formatter = DecimalFormat("#,##0.##", symbols)

    return formatter.format(this / 100.0)
}