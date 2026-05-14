package com.example.domain

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