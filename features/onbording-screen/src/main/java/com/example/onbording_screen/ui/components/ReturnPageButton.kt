package com.example.onbording_screen.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.onbording_screen.model.PreviewPageUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ReturnPageButton(pages:  List<PreviewPageUiState>, pagerState: PagerState, scope: CoroutineScope){
    val returnPageButtonAlpha by animateFloatAsState(
        targetValue = if (pagerState.currentPage < pages.size - 1) 1f else 0f,
        label = "ReturnPageButtonAlpha"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .alpha(returnPageButtonAlpha),
        contentAlignment = Alignment.CenterStart
    ) {
        TextButton(
            onClick = {
                if (pagerState.currentPage < pages.size - 1) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            },
            enabled = pagerState.currentPage > pages.size - 1
        ) {
            Text(
                text = "Пропустить",
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}