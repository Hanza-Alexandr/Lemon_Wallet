package com.example.ui

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.domain.domainmodel.DomainColor


fun DomainColor.toComposeColor(): Color {
    try {
        return Color(this.hex.toColorInt())
    }
    catch (e: IllegalArgumentException){
        throw IllegalArgumentException("❌Ошибка при конвертации цвета в Color: ${e.message}")
    }
}