package com.example.last_operation_block.ui.components.items

import androidx.compose.runtime.Composable
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.TransferOperation

@Composable
fun OperationItem(operation: DomainOperation){
    when(operation){
        is TransferOperation -> TransferOperation(operation)
        is GeneralOperation -> GeneralOperationItem(operation)
    }
}
