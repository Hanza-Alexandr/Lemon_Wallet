package com.example.lemonwallet.ui.view.startscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SkipButton(pages:  List<PreviewPage>, pagerState: PagerState, scope: CoroutineScope, onClickSkip: ()-> Unit){
    val skipButtonAlpha by animateFloatAsState(
        targetValue = if (pagerState.currentPage < pages.size - 1) 1f else 0f,
        label = "SkipButtonAlpha"
    )
    val returnButtonAlpha by animateFloatAsState(
        targetValue = if (pagerState.currentPage > 0) 1f else 0f,
        label = "ReturnButtonAlpha"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 10.dp)

    ) {

        TextButton(
            modifier = Modifier.align(Alignment.CenterEnd).alpha(skipButtonAlpha),
            onClick = {
                onClickSkip()
            },
            enabled = pagerState.currentPage < pages.size - 1
        ) {
            Text(
                text = "Пропустить",
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        }

        //Кнопка возврата
        IconButton(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .alpha(returnButtonAlpha),
            onClick = {
                if (pagerState.currentPage > 0) {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }
            }
        ){
            Icon(
                painter = painterResource(R.drawable.chevron_left),
                contentDescription = null
            )
        }
    }
}