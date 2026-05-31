package com.example.last_operation_block.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.last_operation_block.GetLastOperationsUseCase
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LastOperationBlockViewModel @Inject constructor(
    private val navigator: INavigator,
    getLastOperationsUseCase: GetLastOperationsUseCase,
    ): ViewModel() {
    val lastOperation = getLastOperationsUseCase.invoke(5).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onOperationClick(operation: DomainOperation){
        val isTransfer = operation is TransferOperation
        navigator.navigateTo(NavigationRoute.EditOperation(operation.id, isTransfer))
    }
}