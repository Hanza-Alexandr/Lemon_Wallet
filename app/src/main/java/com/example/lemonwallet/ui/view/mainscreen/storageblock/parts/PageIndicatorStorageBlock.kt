package com.example.lemonwallet.ui.view.mainscreen.storageblock.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonwallet.ui.view.otherelement.PagerIndicators
@Preview
@Composable
fun Test3(){
    val pagerState = rememberPagerState(pageCount = { 1 })
    PageIndicatorStorageBlock(3 ,pagerState)
}

@Composable
fun PageIndicatorStorageBlock(pageCount: Int, pagerState: PagerState){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // Индикаторы страниц (Dots)
        PagerIndicators(
            pageCount = pageCount,
            currentPage = pagerState.currentPage
        )
    }
}