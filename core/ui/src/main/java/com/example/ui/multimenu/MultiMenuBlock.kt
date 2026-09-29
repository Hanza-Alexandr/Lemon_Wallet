package com.example.ui.multimenu

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.storage_block.ui.components.blocks.common.TemplateMainsBlock

@Composable
fun MultiMenuBlock(
    modifier: Modifier = Modifier,
    viewModel: MultiMenuViewModel = hiltViewModel()
    ){
    TemplateMainsBlock({}) {
        LazyRow() {
            item {
                Button(viewModel::toApiScreen) {
                    Text("api screen")
                }
                Button(viewModel::toBleScreen) {
                    Text("ble screen")
                }
            }
        }

    }
}