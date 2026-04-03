package com.example.lemonwallet.ui.view.mainscreen.storageblock.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
@Preview
@Composable
fun Test5(){
    TopBarStorageBlock(8.dp)
}

@Composable
fun TopBarStorageBlock(contentPadding: Dp){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Storage",
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {},
            modifier = Modifier.size(32.dp)
        ) {

        }
    }
}