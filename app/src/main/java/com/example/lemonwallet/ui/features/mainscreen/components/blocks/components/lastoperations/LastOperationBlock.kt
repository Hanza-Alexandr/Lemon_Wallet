package com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.lastoperations

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonwallet.ui.features.mainscreen.components.blocks.common.TemplateMainsBlock
import com.example.lemonwallet.ui.features.mainscreen.components.blocks.common.TopBarLastOperationsBlock

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