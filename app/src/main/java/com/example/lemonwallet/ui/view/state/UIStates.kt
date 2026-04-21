package com.example.lemonwallet.ui.view.state

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage



abstract class GlobalStorageUiState{
    abstract val storage: Storage?
    abstract val isLoading: Boolean
    abstract val name: String
    abstract val note: String?
    abstract val typeStorage: TypeStorage
    abstract val currency: Currency
    abstract val isStatistics: Boolean
    abstract val isArchive: Boolean
    abstract val color: ColorUIState?
    abstract val availableColors: List<ColorUIState>
    abstract val error: String?
    abstract val isSaved: Boolean
}

data class CreateStorageUiState(
    override val storage: Storage? = null,
    override val isLoading: Boolean = false,
    override val name: String = "",
    override val note: String? = null,
    override val typeStorage: TypeStorage = TypeStorage.GENERAL,
    override val currency: Currency = Currency.RUB,
    override val isStatistics: Boolean = true,
    override val isArchive: Boolean = false,
    override val color: ColorUIState? = null,
    override val availableColors: List<ColorUIState> = EnumColor.entries.map { ColorUIState.LocalSystemColor(it) },
    override val error: String? = null,
    override val isSaved: Boolean = false
): GlobalStorageUiState()

data class EditStorageUiState(
    override val storage: Storage? = null,
    override val isLoading: Boolean = true,
    override val name: String = "",
    override val note: String? = null,
    override val typeStorage: TypeStorage = TypeStorage.GENERAL,
    override val currency: Currency = Currency.RUB,
    override val isStatistics: Boolean = true,
    override val isArchive: Boolean = false,
    override val color: ColorUIState? = null,
    override val availableColors: List<ColorUIState> = EnumColor.entries.map { ColorUIState.LocalSystemColor(it) },
    override val error: String? = null,
    override val isSaved: Boolean = false
): GlobalStorageUiState()