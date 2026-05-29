package com.example.room.repository

import android.util.Log
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.NewGeneralOperation
import com.example.domain.domainmodel.NewTransferOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.usecase.GetUserIdUseCase
import com.example.room.dao.OperationDao
import com.example.room.dao.TransferDao
import com.example.room.entity.toDomain
import com.example.room.entity.toRoomEntity
import com.example.room.model.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class OperationRoomRepository @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val operationDao: OperationDao,
    private val transferDao: TransferDao,
) : IOperationRepository {
    override fun getAllTransactionsFlow(): Flow<List<DomainOperation>> {
        val generalOperation =  getUserIdUseCase.getIfFLow().flatMapLatest { userId ->
            operationDao.getAllOperationsWithDetailsFlow(userId).map { operations ->
                operations.map { it.toDomain() }
            }
        }

        val transferOperation =  getUserIdUseCase.getIfFLow().flatMapLatest { userId ->
            transferDao.getAllTransfersWithDetailsFlow(userId).map { operations ->
                operations.map { it.toDomain() }
            }
        }
        return generalOperation.combine(transferOperation) { general, transfer ->
            // Объединяем списки и сортируем
            (general + transfer).sortedWith(
                compareByDescending<DomainOperation> { it.date }
                    .thenByDescending { it.time }
            )
        }
    }

    override suspend fun getTransactionsByStorageFlow(
        storageId: String
    ): Flow<List<DomainOperation>> {
        val generalOperation =  getUserIdUseCase.getIfFLow().flatMapLatest { userId ->
            operationDao.getOperationsByStorageWithDetailsFlow(userId,storageId).map { operations ->
                operations.map { it.toDomain() }
            }
        }

        val transferOperation =  getUserIdUseCase.getIfFLow().flatMapLatest { userId ->
            transferDao.getTransfersByStorageWithDetailsFlow(userId,storageId).map { operations ->
                operations.map { it.toDomain() }
            }
        }
        return generalOperation.combine(transferOperation) { general, transfer ->
            // Объединяем списки и сортируем
            (general + transfer).sortedWith(
                compareByDescending<DomainOperation> { it.date }
                    .thenByDescending { it.time }
            )
        }
        //TODO("Not yet implemented")
    }

    override suspend fun getGeneralOperationById(id: String): GeneralOperation? {
       return operationDao.getOperationWithDetailsByIdFlow(id).firstOrNull()?.toDomain()
    }

    override suspend fun getTransferOperationById(id: String): TransferOperation? {
        return transferDao.getTransferWithDetailsById(id)?.toDomain()
    }

    override suspend fun saveGeneralOperation(operation: NewGeneralOperation) {
        operationDao.insertOperation(operation.toRoomEntity())
    }

    override suspend fun saveTransfer(transfer: NewTransferOperation) {
        transferDao.insertTransfer(transfer.toRoomEntity())
    }

    override suspend fun updateGeneralOperation(operation: GeneralOperation) {
        operationDao.updateOperation(operation.toRoomEntity())
    }

    override suspend fun updateTransfer(transfer: TransferOperation) {
        transferDao.updateTransfer(transfer.toRoomEntity())
    }

    override suspend fun deleteTransaction(operation: DomainOperation): Boolean {
        return try {
            when (operation) {
                is GeneralOperation -> {
                    operationDao.softDeleteOperation(operation.id)
                    true
                }

                is TransferOperation ->{
                    transferDao.softDeleteTransfer(operation.id)
                    true
                }
            }
        } catch (e: Exception){
            false
        }

    }

    override suspend fun migrateGuestData(newUserId: String) {
        TODO("Not yet implemented")
    }

}