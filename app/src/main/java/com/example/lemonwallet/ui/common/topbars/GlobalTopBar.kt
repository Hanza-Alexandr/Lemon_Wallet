package com.example.lemonwallet.ui.common.topbars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonwallet.ui.theme.MainDark

@Composable
fun GlobalTopBar(localTopBar: @Composable ()-> Unit){
    Surface(
        modifier = Modifier.fillMaxWidth()
            .statusBarsPadding(),
        color = MainDark
    ) {
        localTopBar()
    }
}

@Preview
@Composable
fun GlobalTopBarPreview(){
    GlobalTopBar {  }
}