package com.example.last_operation_block.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.reposytory.IOperationRepository
import com.example.domain.settings.ISettingsRepository
import com.example.last_operation_block.GetOperationsUseCase
import com.example.last_operation_block.GetSelectStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LastOperationViewModel @Inject constructor(
    getOperationsUseCase: GetOperationsUseCase,
    ): ViewModel() {
    val lastOperation = getOperationsUseCase.invoke().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}