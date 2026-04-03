package com.example.lemonwallet.model.service

import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.StateDomain
import com.example.lemonwallet.model.StateDomainList
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage

interface IStorageService{
    fun getStorageList(): StateDomainList<Storage>
    fun getStorage(storageId: Int): StateDomain<Storage>
    fun createStorage(name: String, currency: Currency, typeStorage: TypeStorage, note: String?, color: ExistColor): StateDomain<Storage>
    fun updateStorage(changingStorage: Storage, name: String?, typeStorage: TypeStorage?, note: String?, color: ExistColor?, isStatistic: Boolean?, isArchive: Boolean?): StateDomain<Storage>
    fun deleteStorage(storage: Storage): StateDomain<Storage>
    fun getStorageBalance(storage: Storage): StateDomain<Double>
}