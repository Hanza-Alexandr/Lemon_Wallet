package com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.lastoperations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.model.domain.Operation

@Composable
fun LastOperationList(
    elementHeight: Int = 65,
    operations: List<Operation> = emptyList(),
    onOperationClick: (index: Int) -> Unit = {}
){
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