package com.example.domain

import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.NewDomainColor

interface IEditStorage {
    fun onNameChange(newName: String)
    fun onNoteChange(newNote: String)
    fun onTypeChange(newType: TypeStorage)
    fun onCurrencyChange(newCurrency: Currency)
}