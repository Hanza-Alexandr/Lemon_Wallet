package com.example.detailoperation_screen.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.detailoperation_screen.usecase.UpdateOperationUseCase
import com.example.detailoperation_screen.model.UIStatesDetailGeneralOperations
import com.example.detailoperation_screen.model.UiStateTypeOperation
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.reposytory.IStorageRepository
import com.example.detailoperation_screen.usecase.CalculateExpressionUseCase
import com.example.domain.usecase.GetTopCategoriesUseCase
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import com.example.detailoperation_screen.ui.components.ConvertDomainCategoryToUiModel
import com.example.detailoperation_screen.ui.components.ConvertDomainStorageToUiModel
import com.example.detailoperation_screen.ui.components.StorageUiModel
import com.example.detailoperation_screen.ui.components.UiModelCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass


@HiltViewModel
class EditOperationViewModel @Inject constructor(
    private val navigator: INavigator,
    private val storageRepo: IStorageRepository,
    private val operationRepo: IOperationRepository,
    private val getTopCategoriesUseCase: GetTopCategoriesUseCase,
    private val covertDomainCategoryToUiModel: ConvertDomainCategoryToUiModel,
    private val covertDomainStorageToUiModel: ConvertDomainStorageToUiModel,
    private val stateHandle: SavedStateHandle,
    private val calculateExpressionUseCase: CalculateExpressionUseCase,
    private val updateOperationUseCase: UpdateOperationUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIStatesDetailGeneralOperations())
    val uiState = _uiState.asStateFlow()
    init{
        val operationId = stateHandle.toRoute<NavigationRoute.EditOperation>().operationId
        val isTransfer = stateHandle.toRoute<NavigationRoute.EditOperation>().isTransfer

        viewModelScope.launch {
            val storages = storageRepo.getAllStoragesFlow().first()
            val categories = getTopCategoriesUseCase.invoke()
            val operation: DomainOperation = if (isTransfer){
                operationRepo.getTransferOperationById(operationId) ?: throw IllegalStateException("Операция не найдена")
            }else{
                operationRepo.getGeneralOperationById(operationId) ?: throw IllegalStateException("Операция не найдена")
            }

            val uiStateOperation = when(operation){
                is TransferOperation -> {
                    UiStateTypeOperation.TransferUiStateTypeOperation(
                        fromStorageList = storages.map {
                            covertDomainStorageToUiModel.invoke(it)
                        }.map {
                            if (it.storage.id == operation.fromStorage.id) it.copy(isSelected = true) else it
                        },
                        toStorageList = storages.map {
                            covertDomainStorageToUiModel.invoke(it)
                        }.map {
                            if (it.storage.id == operation.toStorage.id) it.copy(isSelected = true) else it
                        }
                    )
                }
                is DebitOperation -> UiStateTypeOperation.GeneralOperationUiStateTypeOperation(
                    isDebit = true,
                    categories = categories.map {
                        covertDomainCategoryToUiModel.invoke(it)
                    }.map {
                        if (it.category.id == operation.category.id) it.copy(isSelect = true) else it
                    },
                    storageList = storages.map {
                        covertDomainStorageToUiModel.invoke(it)
                    }.map {
                        if (it.storage.id == operation.storage.id) it.copy(isSelected = true) else it
                    },
                )
                is CreditOperation -> UiStateTypeOperation.GeneralOperationUiStateTypeOperation(
                    isDebit = false,
                    categories = categories.map {
                        covertDomainCategoryToUiModel.invoke(it)
                    }.map {
                        if (it.category.id == operation.category.id) it.copy(isSelect = true) else it
                    },
                    storageList = storages.map {
                        covertDomainStorageToUiModel.invoke(it)
                    }.map {
                        if (it.storage.id == operation.storage.id) it.copy(isSelected = true) else it
                    },
                )
                else -> throw IllegalStateException("Неизвестный тип операции")
                }

            _uiState.update {
                it.copy(
                    result = operation.amount,
                    expression = operation.amount.toString(),
                    uiStateTypeOperation = uiStateOperation,
                    date = operation.date,
                    time = operation.time,
                    note = operation.comment,
                )
            }
        }
    }

    fun onBack(){
        viewModelScope.launch {
            navigator.goBack()
        }
    }

    fun onMoreCategory(){
        navigator.navigateTo(NavigationRoute.CategorySelectScreen)
    }

    fun onStorageAdded(){
        navigator.navigateTo(NavigationRoute.CreateStorage)
    }

    fun onUpdate(){
        viewModelScope.launch {
            val operationId = stateHandle.toRoute<NavigationRoute.EditOperation>().operationId
            val isTransfer = stateHandle.toRoute<NavigationRoute.EditOperation>().isTransfer
            val operation: DomainOperation = if (isTransfer){
                operationRepo.getTransferOperationById(operationId) ?: throw IllegalStateException("Операция не найдена")
            }else{
                operationRepo.getGeneralOperationById(operationId) ?: throw IllegalStateException("Операция не найдена")
            }
            updateOperationUseCase.invoke(uiState.value,operation)
            navigator.goBack()
        }
    }

    fun onDelete(){
        viewModelScope.launch {
            val operationId = stateHandle.toRoute<NavigationRoute.EditOperation>().operationId
            val isTransfer = stateHandle.toRoute<NavigationRoute.EditOperation>().isTransfer
            val operation: DomainOperation = if (isTransfer){
                operationRepo.getTransferOperationById(operationId) ?: throw IllegalStateException("Операция не найдена")
            }else{
                operationRepo.getGeneralOperationById(operationId) ?: throw IllegalStateException("Операция не найдена")
            }
            operationRepo.deleteTransaction(operation)
            navigator.goBack()
        }
    }

    fun onChangeTypeOperation (type: KClass<out DomainOperation>){
        viewModelScope.launch {
            val storages= when(val type = _uiState.value.uiStateTypeOperation){
                is UiStateTypeOperation.GeneralOperationUiStateTypeOperation -> type.storageList
                is UiStateTypeOperation.TransferUiStateTypeOperation -> type.fromStorageList
                else -> emptyList()
            }

            val category = when(val type = _uiState.value.uiStateTypeOperation){
                is UiStateTypeOperation.GeneralOperationUiStateTypeOperation -> type.categories
                else -> getTopCategoriesUseCase.invoke().map { covertDomainCategoryToUiModel.invoke(it) }
            }
            when(type){
                DebitOperation::class ->{
                    _uiState.update {
                        it.copy(uiStateTypeOperation = UiStateTypeOperation.GeneralOperationUiStateTypeOperation(
                            isDebit = true,
                            categories = category,
                            storageList =storages
                        ))
                    }
                }
                CreditOperation::class ->{
                    _uiState.update {
                        it.copy(uiStateTypeOperation = UiStateTypeOperation.GeneralOperationUiStateTypeOperation(
                            isDebit = false,
                            categories = category,
                            storageList =storages
                        ))
                    }
                }
                TransferOperation::class ->{
                    _uiState.update {
                        it.copy(uiStateTypeOperation = UiStateTypeOperation.TransferUiStateTypeOperation(
                            fromStorageList =storages,
                            toStorageList = storages.map {
                                it.copy(isSelected = false)
                            }
                        ))
                    }
                }
            }
        }

    }
    fun onFromStorageSelected(storage: StorageUiModel){
        val type = _uiState.value.uiStateTypeOperation
        when (type){
            is UiStateTypeOperation.GeneralOperationUiStateTypeOperation -> {
                _uiState.update { it.copy(uiStateTypeOperation = UiStateTypeOperation.GeneralOperationUiStateTypeOperation(
                    type.isDebit,
                    type.categories,
                    type.storageList.map { it.copy(isSelected = false)}.map { if (it == storage) it.copy(isSelected = true) else it})) }
            }
            is UiStateTypeOperation.TransferUiStateTypeOperation ->{
                _uiState.update { it.copy(uiStateTypeOperation = UiStateTypeOperation.TransferUiStateTypeOperation(
                    fromStorageList = type.fromStorageList.map { it.copy(isSelected = false)}.map { if (it == storage) it.copy(isSelected = true) else it},
                    toStorageList = type.toStorageList)) }
            }
            else -> {

            }
        }
    }
    fun onToStorageSelected(storage: StorageUiModel) {
        val type = _uiState.value.uiStateTypeOperation
        when (type) {
            is UiStateTypeOperation.GeneralOperationUiStateTypeOperation -> {

            }
            is UiStateTypeOperation.TransferUiStateTypeOperation -> {
                _uiState.update {
                    it.copy(
                        uiStateTypeOperation = UiStateTypeOperation.TransferUiStateTypeOperation(
                            fromStorageList = type.fromStorageList,
                            toStorageList = type.toStorageList.map { it.copy(isSelected = false) }
                                .map { if (it == storage) it.copy(isSelected = true) else it })
                    )
                }
            }
            else -> {}
        }
    }
    fun onCategorySelected(category: UiModelCategory){
        val type = _uiState.value.uiStateTypeOperation
        if (type is UiStateTypeOperation.GeneralOperationUiStateTypeOperation){
            _uiState.update {
                it.copy(uiStateTypeOperation = UiStateTypeOperation.GeneralOperationUiStateTypeOperation(
                    type.isDebit,
                    type.categories.map { it.copy(isSelect = false)}.map { if (it.category.id == category.category.id) it.copy(isSelect = true) else it},
                    type.storageList
                )
                )
            }
        }
    }
    fun onKeyClick(key: String){
        _uiState.update { currentState ->
            val result = calculateExpressionUseCase.execute(
                currentExpression = currentState.expression,
                keyPressed = key
            )

            currentState.copy(
                expression = result.expression,
                result = result.amount
            )
        }
    }
}
