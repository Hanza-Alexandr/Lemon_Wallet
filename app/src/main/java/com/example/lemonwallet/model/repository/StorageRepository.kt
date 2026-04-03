package com.example.lemonwallet.model.repository

import androidx.compose.ui.graphics.Color
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.NewStorage
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.model.domain.TypeStorage

class StorageLocalRepository(): IStorageRepository{

    private val col1 = SystemColor.create(1,"#FF74B65F")
    private val col2 = SystemColor.create(2,"#FFffcf40")
    private val col3 = SystemColor.create(3,"#FF2F8EFF")

    private val storages = listOf<Storage>(
        Storage(
            id = 1,
            name = "Sber",
            userId = -1,
            currency = Currency.RUB,
            typeStorage = TypeStorage.BANK_ACCOUNT,
            note = null,
            color = col1,
            isStatistics = true,
            isArchive = false
        ),
        Storage(
            id = 2,
            name = "T-bank",
            userId = -1,
            currency = Currency.RUB,
            typeStorage = TypeStorage.BANK_ACCOUNT,
            note = null,
            color = col2,
            isStatistics = true,
            isArchive = false
        ),
        Storage(
            id = 3,
            name = "VTB",
            userId = -1,
            currency = Currency.RUB,
            typeStorage = TypeStorage.BANK_ACCOUNT,
            note = null,
            color = col3,
            isStatistics = true,
            isArchive = false
        )
    )

    override fun getAll(): List<Storage> {
        return storages
    }

    override fun getById(id: Long): Storage? {
        TODO("Not yet implemented")
    }

    override fun save(storage: Storage): Storage? {
        TODO("Not yet implemented")
    }

    override fun save(storage: NewStorage): Storage? {
        TODO("Not yet implemented")
    }

    override fun delete(storage: Storage): Storage? {
        TODO("Not yet implemented")
    }

}