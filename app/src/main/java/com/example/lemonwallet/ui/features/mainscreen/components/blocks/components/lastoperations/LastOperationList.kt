package com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.lastoperations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.domain.Operation

@Composable
fun LastOperationList(
    elementHeight: Int = 65,
    operations: List<Operation> = emptyList(),
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