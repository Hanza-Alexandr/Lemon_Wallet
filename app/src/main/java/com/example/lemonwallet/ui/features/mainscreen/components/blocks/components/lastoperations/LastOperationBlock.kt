package com.example.storage_block.ui.components.blocks.components.lastoperations

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.lastoperations.LastOperationList
import com.example.storage_block.ui.components.blocks.common.TemplateMainsBlock
import com.example.storage_block.ui.components.blocks.common.TopBarLastOperationsBlock

@Preview
@Composable
fun LastOperationBlockPreview(){
    LastOperationBlock()
}

@Composable
fun LastOperationBlock(){
    TemplateMainsBlock(
        topBar = ::TopBarLastOperationsBlock,
    ) {
        LastOperationList()
    }
}