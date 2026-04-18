package com.example.lemonwallet.ui.view.topbars

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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