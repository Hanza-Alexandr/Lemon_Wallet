package com.example.last_operation_block.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.GeneralOperation
import com.example.domain.domainmodel.TransferOperation
import com.example.last_operation_block.ui.components.items.EmptyOperations
import com.example.last_operation_block.ui.components.items.OperationItem

@Composable
fun LastOperationList(
    vararg storages: DomainStorage,
    elementHeight: Int = 65,
    operations: List<DomainOperation> = emptyList(),
    onOperationClick: (index: Int) -> Unit = {}

){
    // Генерация спика и применение фильтров происходит отдельно
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if(operations.isEmpty()) {
            EmptyOperations()
        } else {
            for (operation in operations) {
                OperationItem(operation)
            }
        }
    }
}