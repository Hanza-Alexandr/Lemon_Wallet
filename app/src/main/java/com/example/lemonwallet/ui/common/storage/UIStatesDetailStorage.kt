package com.example.lemonwallet.ui.common.storage

import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.ui.common.color.state.ColorUIState

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
