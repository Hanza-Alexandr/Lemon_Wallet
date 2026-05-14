package com.example.storage_block.model

import com.example.domain.Currency
import com.example.domain.EnumColor
import com.example.domain.Storage
import com.example.domain.SystemColor
import com.example.domain.TypeStorage


val storagesLOCALTESTDATA = listOf(
    Storage.create(
        id = 1,
        name = "Sber",
        userId = -1,
        currency = Currency.RUB,
        typeStorage = TypeStorage.BANK_ACCOUNT,
        note = null,
        color = SystemColor(1, EnumColor.ORANGE.hexCode),
        isStatistics = true,
        isArchive = false
    ),
    Storage.create(
        id = 2,
        name = "Sber",
        userId = -1,
        currency = Currency.RUB,
        typeStorage = TypeStorage.BANK_ACCOUNT,
        note = null,
        color = SystemColor(1, EnumColor.GREEN.hexCode),
        isStatistics = true,
        isArchive = false
    ),
    Storage.create(
        id = 3,
        name = "Sber",
        userId = -1,
        currency = Currency.RUB,
        typeStorage = TypeStorage.BANK_ACCOUNT,
        note = null,
        color = SystemColor(1, EnumColor.BLUE.hexCode),
        isStatistics = true,
        isArchive = false
    ),
)
val storageLOCALTESTDATA = storagesLOCALTESTDATA[1]
