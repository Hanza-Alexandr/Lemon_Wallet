package com.example.ui

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.domain.ColorUIState
import com.example.domain.ColorUIState.DataBaseColor
import com.example.domain.ColorUIState.LocalSystemColor

fun ColorUIState.toColor(): Color {
    return when(this){
        is LocalSystemColor -> Color(this.color.hexCode.toColorInt())
        is DataBaseColor -> Color(this.color.hex.toColorInt())
    }

}