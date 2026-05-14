package com.example.onbording_screen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onbording_screen.model.PreviewPageUiState
import kotlinx.coroutines.CoroutineScope

@Composable
fun PagePart(
    pages:  List<PreviewPageUiState>,
    pagerState: PagerState,
    scope: CoroutineScope,
    onFinished: () -> Unit
){
    Column(modifier = Modifier.fillMaxSize()) {
        // Основной слайдер (Pager)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { position ->
            val page = pages[position]
            // Фиксируем высоту контента, чтобы элементы не прыгали
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Иконка в круге
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(page.mainColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    EmojiIcon(emoji = page.emoji, fontSize = 64.sp)
                }

                Spacer(modifier = Modifier.height(48.dp))

                Text(
                    text = page.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp,
                    color = Color(0xFF1F2937),
                    modifier = Modifier.heightIn(min = 68.dp) // Минимум 2 строки для текста
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    lineHeight = 24.sp,
                    modifier = Modifier.heightIn(min = 72.dp) // Минимум 3 строки для описания
                )
            }
        }
        BottomPart(pages,pagerState,scope, onFinished)
    }
}