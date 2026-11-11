package com.example.api_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ApiScreen(){
    Column(Modifier.systemBarsPadding()) {
        Button(onClick = {
            throw NullPointerException()
        }) { }
    }
}