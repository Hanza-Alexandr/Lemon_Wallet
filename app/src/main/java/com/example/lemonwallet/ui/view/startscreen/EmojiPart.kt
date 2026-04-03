package com.example.lemonwallet.ui.view.startscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun EmojiIcon(
    emoji: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp
) {
    Text(
        text = emoji,
        fontSize = fontSize,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}