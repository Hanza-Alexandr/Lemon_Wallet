package com.example.bdui_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BDUIScreen(modifier: Modifier = Modifier){
    Column(
        modifier
            .fillMaxSize()
            .systemBarsPadding(),
        Arrangement.Center,
        Alignment.CenterHorizontally) {

        CircularProgressIndicator(
            color = Color.Blue
        )

    }

}