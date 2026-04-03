package com.example.lemonwallet.ui.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Test(){
    PagerIndicators(3,1)
}

@Composable
fun PagerIndicators(pageCount: Int, currentPage: Int, dotSizeCof: Float = 1f){
    // Точки-индикаторы
    val selectedDotSize = 12 * dotSizeCof
    val notSelectedDotSize = 8 * dotSizeCof
    val boxSize = 24 * dotSizeCof/1
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { iteration ->
            val isSelected = iteration==currentPage

            val size by animateDpAsState(
                targetValue = if (isSelected) selectedDotSize.dp else notSelectedDotSize.dp,
                label = "IndicatorSize"
            )

            val color by animateColorAsState(
                targetValue = if (isSelected) Color.DarkGray else Color.White.copy(alpha = 0.5f),
                label = "IndicatorColor"
            )
            Box(
                modifier = Modifier.size(boxSize.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(size)
                )
            }

        }
    }
}