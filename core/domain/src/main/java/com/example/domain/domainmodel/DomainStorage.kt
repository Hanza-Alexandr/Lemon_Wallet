package com.example.domain.domainmodel

import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.state.DomainState

data class DomainStorage(
    val id: String,
    val name: String,
    val userId: String,
    val currency: Currency,
    val typeStorage: TypeStorage,
    val note: String?,
    val color: DomainColor?
)

data class NewDomainStorage(
    val name: String,
    val userId: String,
    val currency: Currency,
    val typeStorage: TypeStorage,
    val note: String?,
    val color: DomainColor?
)