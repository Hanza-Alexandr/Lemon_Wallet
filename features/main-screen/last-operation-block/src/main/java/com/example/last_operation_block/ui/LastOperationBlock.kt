package com.example.storage_block.ui.components.blocks.components.lastoperations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.last_operation_block.ui.LastOperationViewModel
import com.example.last_operation_block.ui.components.LastOperationList
import com.example.storage_block.ui.components.blocks.common.TemplateMainsBlock
import com.example.storage_block.ui.components.blocks.common.TopBarLastOperationsBlock

@Preview
@Composable
fun LastOperationBlockPreview(){
    LastOperationBlock()
}

@Composable
fun LastOperationBlock(
    viewModel: LastOperationViewModel = hiltViewModel(),
){
    val lastOperations by viewModel.lastOperation.collectAsStateWithLifecycle()

    TemplateMainsBlock(
        topBar = ::TopBarLastOperationsBlock,
    ) {
        LastOperationList(
            operations = lastOperations
        )
    }
}