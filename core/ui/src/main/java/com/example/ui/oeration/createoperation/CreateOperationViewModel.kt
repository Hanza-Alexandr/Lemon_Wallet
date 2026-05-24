package com.example.ui.oeration.createoperation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.usecase.CalculateExpressionUseCase
import com.example.domain.usecase.GetTopCategoriesUseCase
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import com.example.ui.oeration.UIStatesDetailGeneralOperations
import com.example.ui.oeration.UiStateTypeOperation
import com.example.ui.oeration.components.ConvertDomainStorageToUiModel
import com.example.ui.oeration.components.StorageUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KClass

@HiltViewModel
class CreateOperationViewModel @Inject constructor(
    private val navigator: INavigator,
    private val storageRepo: IStorageRepository,
    private val getTopCategoriesUseCase: GetTopCategoriesUseCase,
    private val covertDomainStorageToUiModel: ConvertDomainStorageToUiModel,
    private val stateHandle: SavedStateHandle,
    private val calculateExpressionUseCase: CalculateExpressionUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIStatesDetailGeneralOperations())
    val uiState = _uiState.asStateFlow()

    init{
        val storageId = stateHandle.toRoute<NavigationRoute.CreateOperation>().storageId
        viewModelScope.launch {
            val storages = storageRepo.getAllStoragesFlow().first()
            val categories = getTopCategoriesUseCase.invoke()
            _uiState.update {
                it.copy(
                uiStateTypeOperation = UiStateTypeOperation.GeneralOperationUiStateTypeOperation(
                    isDebit = true,
                    categories = categories,
                    storageList = storages.map {
                        covertDomainStorageToUiModel.invoke(it)
                    }.map {
                        if (it.id == storageId) it.copy(isSelected = true) else it
                    },
                ),
            ) }
        }
    }

    fun onSetSelectCategories(){

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
                else -> getTopCategoriesUseCase.invoke()
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
    fun onCategorySelected(category: DomainCategory){

    }
    fun onKeyClick(key: String){
        _uiState.update { currentState ->
            val result = calculateExpressionUseCase.execute(
                currentExpression = currentState.expression,
                keyPressed = key
            )

            currentState.copy(
                expression = result.expression,
                result = result.evaluatedResult
            )
        }
    }
}