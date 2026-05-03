package com.example.storage_block.ui.components.blocks.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.ui.theme.MainLight

@Preview
@Composable
fun TemplateMainsBlockPreview() {
    TemplateMainsBlock(
        topBar = {},
        content = {}
    )
}

@Composable
fun TemplateMainsBlock(topBar: @Composable (horizontalContentPadding: Dp) -> Unit, bottomBar: @Composable (horizontalContentPadding: Dp) -> Unit = {}, content: @Composable () -> Unit){
    val horizontalContentPadding = 8.dp //Горизонтальный отступ контента от краев
    val roundedCornerShapeBlock = 6.dp //Скругление углов блока
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundedCornerShapeBlock))
            .background(MainLight) // Light Gray
    ) {
        topBar(horizontalContentPadding)
        content()
        bottomBar(horizontalContentPadding)

    }
}