package com.example.detailstorage_screen.model

import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainColor

data class UIStatesDetailStorage(
    val name: String?= null,
    val note: String? = null,
    val typeStorage: TypeStorage? = null,
    val currency: Currency? = null,
    val color: DomainColor? = null,
    val availableColors: List<DomainColor> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false,
)