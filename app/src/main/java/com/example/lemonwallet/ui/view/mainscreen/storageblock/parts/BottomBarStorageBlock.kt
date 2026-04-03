package com.example.lemonwallet.ui.view.mainscreen.storageblock.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Test6(){
BottomBarStorageBlock(8.dp)
}
@Composable
fun BottomBarStorageBlock(contentHorizontalPadding: Dp){
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = contentHorizontalPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {}
        ) { }
        Button(
            onClick = {}
        ) { }
    }
}