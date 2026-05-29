package com.example.last_operation_block.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.last_operation_block.ui.components.items.EmptyOperations
import com.example.last_operation_block.ui.components.items.GeneralOperationItem
import com.example.last_operation_block.ui.components.items.TransferOperationItem

@Composable
fun LastOperationList(
    vararg storages: DomainStorage,
    elementHeight: Dp = 65.dp,
    operations: List<DomainOperation>,
    onOperationClick: (operation: DomainOperation) -> Unit
){
    Log.d("LastOperationList", "${operations.size}")
    // Генерация спика и применение фильтров происходит отдельно
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        if(operations.isEmpty()) {
            EmptyOperations()
        } else {
            operations.forEach { operation ->
                when(operation){
                    is TransferOperation -> TransferOperationItem(operation, Modifier, onOperationClick)
                    is GeneralOperation -> GeneralOperationItem(operation, onOperationClick)
                }
            }
        }
    }
}