package com.example.ui.oeration

import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.DomainStorage
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock


data class UIStatesDetailGeneralOperations(
    val amount: Long?= null,
    val isDebit: Boolean? = null,
    val category: DomainCategory? = null,
    val storage: DomainStorage? = null,
    val toStorage: DomainStorage? = null, // МБ сделать общий UIState и для перевода и для обычнх операцйи
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val note: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)