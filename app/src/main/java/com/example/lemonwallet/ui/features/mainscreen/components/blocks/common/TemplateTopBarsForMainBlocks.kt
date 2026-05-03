package com.example.storage_block.ui.components.blocks.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.R

@Preview
@Composable
fun TopBarStorageBlockPreview(){
    Column() {
        TopBarStorageBlock(8.dp)
        TopBarLastOperationsBlock(8.dp)
    }


}

@Composable
fun TemplateTobBarMainBlocks(contentHorizontalPadding: Dp,content: @Composable (iconSize: Dp) -> Unit){

    val iconSize = 22.dp // TODO(Доделать - настроить размер и контейнеры так что бы иконки не создавали сильно много места вокруг себя)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = contentHorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content(iconSize)
    }
}
@Composable
fun TopBarStorageBlock(contentHorizontalPadding: Dp){

    TemplateTobBarMainBlocks(contentHorizontalPadding){ iconSize ->
        Text(
            text = "Счет",
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { TODO("NOT IMPLEMENT") }) {
            Icon(painterResource(R.drawable.reorder), contentDescription = "Settings")
        }
    }
}

@Composable
fun TopBarLastOperationsBlock(contentHorizontalPadding: Dp){
    TemplateTobBarMainBlocks(contentHorizontalPadding) { iconSize ->
        Text(
            text = "Последние операции",
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { TODO("NOT IMPLEMENT") }) {
            Icon(painter = painterResource(R.drawable.filter_list), contentDescription = "Settings")
        }
    }
}