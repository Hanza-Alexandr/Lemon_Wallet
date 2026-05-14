package com.example.domain

sealed class ColorUIState(){
    data class LocalSystemColor(val color: EnumColor): ColorUIState()
    data class DataBaseColor(val color: ExistColor): ColorUIState()
    
}

