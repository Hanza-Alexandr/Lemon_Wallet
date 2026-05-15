package com.example.last_operation_block.ui.components.items

import androidx.compose.runtime.Composable
import com.example.domain.GeneralTransaction
import com.example.domain.Operation
import com.example.domain.TransferTransaction

@Composable
fun OperationItem(operation: Operation){
    when(operation){
        is GeneralTransaction -> GeneralTransaction(operation)
        is TransferTransaction -> TransferOperation(operation)
        else -> {

        }
    }

}
