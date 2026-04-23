package com.example.lemonwallet.viewmodel

import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.NewColor
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.ui.state.ColorUIState

interface IEditStorage {
    fun onSaveColor(newColor: NewColor)
    fun onNameChange(newName: String)
    fun onNoteChange(newNote: String)
    fun onTypeChange(newType: TypeStorage)
    fun onCurrencyChange(newCurrency: Currency)
    fun onStatisticsChange(value: Boolean)
    fun onArchiveChange(value: Boolean)
    fun onColorChange(newColor: ColorUIState?)
    fun toggleColorDeleteMode(enabled: Boolean)
    fun deleteColor(colorUiState: ColorUIState)

}