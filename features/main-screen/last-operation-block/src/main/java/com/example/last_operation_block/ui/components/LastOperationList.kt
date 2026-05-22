package com.example.last_operation_block.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.DomainOperation

@Composable
fun LastOperationList(
    elementHeight: Int = 65,
    operations: List<DomainOperation> = emptyList(),
    onOperationClick: (index: Int) -> Unit = {}
){
    // Генерация спика и применение фильтров происходит отдельно
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if(operations.isEmpty()) {
            // TODO: Add placeholder
        } else {
            for (operation in operations) {
                // TODO: Render operation item
            }
        }
    }
}