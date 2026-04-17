package com.example.lemonwallet.model.service

import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.state.DomainState
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.model.repository.IStorageRepository
import com.example.lemonwallet.model.repository.PreferencesDataStore
import com.example.lemonwallet.model.repository.StorageRoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindStorageRepository(
        impl: StorageRoomRepository // Что Hilt должен СОЗДАТЬ
    ): IStorageRepository           // Под видом КАКОГО интерфейса отдать
}
class StorageService @Inject constructor(private val storageRepo: IStorageRepository, private val dataStorePref: PreferencesDataStore): IStorageService {

    inner class UIStorageService() {
        // Поток остается таким же — он просто наблюдает
        val stateSelectedStorages= combine(
            storageRepo.getAll(),
            dataStorePref.UIState().indexesSelectedStorageFlow
        ) { storages, savedState ->
            val validIndexes = storages.indices.toSet()
            savedState.intersect(validIndexes)
        }
        suspend fun onSelect(isLongClick: Boolean, index: Int) {
            val currentSelected = dataStorePref.UIState().indexesSelectedStorageFlow.first()
            val storages = storageRepo.getAll().first()

            val isAlreadySelected = currentSelected.contains(index)
            val isSelectModeActive = currentSelected.size > 1

            val newSelected = currentSelected.toMutableSet().apply {
                when {
                    // 1. Долгий клик: всегда приводит к выделению одного или добавлению в стек
                    isLongClick -> {
                        if (isSelectModeActive) clear() // Твоя новая логика: сброс мультивыбора
                        add(index)
                    }

                    // 2. Обычный клик по уже выделенному: пробуем снять выделение
                    isAlreadySelected -> {
                        if (size > 1) remove(index)
                    }

                    // 3. Обычный клик по новому элементу:
                    // если уже в режиме выбора — добавляем, если нет — переключаем (одиночный выбор)
                    else -> {
                        if (size == 1) clear()
                        add(index)
                    }
                }
            }

            // Валидация и сохранение
            val validIndexes = storages.indices.toSet()
            dataStorePref.UIState().saveSelectedIds(newSelected.intersect(validIndexes))
        }
    }
    override fun getFlowStorageList(): Flow<List<Storage>> {
        return storageRepo.getAll()
    }

    override suspend fun getStorage(storageId: Int): DomainState<Storage> {
        TODO("Not yet implemented")
    }

    override suspend fun createStorage(
        name: String,
        currency: Currency,
        typeStorage: TypeStorage,
        note: String?,
        color: ExistColor
    ): DomainState<Storage> {
        TODO("Not yet implemented")
    }

    override suspend fun updateStorage(
        changingStorage: Storage,
        name: String?,
        typeStorage: TypeStorage?,
        note: String?,
        color: ExistColor?,
        isStatistic: Boolean?,
        isArchive: Boolean?
    ): DomainState<Storage> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteStorage(storage: Storage): DomainState<Storage> {
        TODO("Not yet implemented")
    }

    override fun getStorageBalance(storage: Storage): DomainState<Double> {
        return when(storage.id){
            1L -> DomainState.Success(2000.0)
            2L -> DomainState.Success(5000.0)
            3L -> DomainState.Success(12345.34)
            else -> DomainState.Success(0.0)
        }
    }
}