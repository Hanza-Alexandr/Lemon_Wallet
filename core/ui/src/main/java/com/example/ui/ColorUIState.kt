package com.example.ui

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.domain.EnumColor
import com.example.domain.ExistColor

sealed class ColorUIState(){
    data class LocalSystemColor(val color: EnumColor): ColorUIState()
    data class DataBaseColor(val color: ExistColor): ColorUIState()
    fun toColor(): Color {
        return when(this){
            is LocalSystemColor -> Color(this.color.hexCode.toColorInt())
            is DataBaseColor -> Color(this.color.hex.toColorInt())
        }
    }
}