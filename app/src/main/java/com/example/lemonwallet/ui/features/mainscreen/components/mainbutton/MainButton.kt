package com.example.lemonwallet.ui.features.mainscreen.components.mainbutton

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    onSizeGanged: (IntSize)-> Unit,
    onClick: ()-> Unit
){
    Box(
        modifier = modifier
            .onSizeChanged(onSizeGanged)
            .padding(16.dp)

    ){
        Button(
            modifier = Modifier.size(60.dp),
            onClick = {
                onClick()
            }
        ) { }
    }
}