package com.example.domain.usecase

import com.example.domain.domainmodel.DomainStorage
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSelectStorageUseCase @Inject constructor(
    private val settings: ISettingsRepository,
    private val storageRepository: IStorageRepository
) {
    operator fun invoke(): Flow<List<DomainStorage>>{
        val allStorage = storageRepository.getAllStoragesFlow()
        val selectedStorageId = settings.idSelectedStorageFlow
        return allStorage.combine(selectedStorageId){ storages, ids ->
            storages.filter { storage ->
                ids.contains(storage.id)
            }
        }
    }
}