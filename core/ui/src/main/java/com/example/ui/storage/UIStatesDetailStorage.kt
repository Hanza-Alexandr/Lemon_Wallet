package com.example.ui.storage

import com.example.domain.ColorUIState
import com.example.domain.Currency
import com.example.domain.EnumColor
import com.example.domain.Storage
import com.example.domain.TypeStorage

data class DefaultStateDetailsStorage(
    val storage: Storage? = null,
    val isLoading: Boolean = false,
    val name: String = "",
    val note: String? = null,
    val typeStorage: TypeStorage = TypeStorage.GENERAL,
    val currency: Currency = Currency.RUB,
    val isStatistics: Boolean = true,
    val isArchive: Boolean = false,
    val color: ColorUIState? = null,
    val availableColors: List<ColorUIState> = EnumColor.entries.map { ColorUIState.LocalSystemColor(it) },
    val error: String? = null,
    val isSaved: Boolean = false,
    val isColorDeleteMode: Boolean = false
)
