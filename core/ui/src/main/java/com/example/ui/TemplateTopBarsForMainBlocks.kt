package com.example.storage_block.ui.components.blocks.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.R


@Preview
@Composable
fun TopBarStorageBlockPreview(){
    Column() {
        TopBarStorageBlock(8.dp)
        TopBarLastOperationsBlock(8.dp)
    }


}

@Composable
fun TemplateTobBarMainBlocks(contentHorizontalPadding: Dp,content: @Composable (iconSize: Dp) -> Unit){

    val iconSize = 22.dp // TODO(Доделать - настроить размер и контейнеры так что бы иконки не создавали сильно много места вокруг себя)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = contentHorizontalPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content(iconSize)
    }
}
@Composable
fun TopBarStorageBlock(contentHorizontalPadding: Dp){

    TemplateTobBarMainBlocks(contentHorizontalPadding){ iconSize ->
        Text(
            text = "Счет",
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { TODO("NOT IMPLEMENT") }) {
            Icon(painterResource(R.drawable.reorder), contentDescription = "Settings")
        }
    }
}


@Composable
fun TopBarLastOperationsBlock(
    contentHorizontalPadding: Dp,
    filterName: String = "1 day", // Текст по центру
    onFilterClick: () -> Unit = {},
    onViewMoreClick: () -> Unit = {}
) {
    TemplateTobBarMainBlocks(contentHorizontalPadding) { iconSize ->
        // 1. Левая часть: Иконка фильтра
        IconButton(onClick = onFilterClick) {
            Icon(
                painter = painterResource(R.drawable.filter_list),
                contentDescription = "Filter",
                modifier = Modifier.size(iconSize)
            )
        }

        // 2. Центральная часть: Текст фильтра
        Text(
            text = filterName,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyLarge
        )

        // 3. Правая часть: Кнопка "VIEW MORE" с иконкой
        TextButton(
            onClick = onViewMoreClick,
            // Убираем лишние отступы внутри кнопки, чтобы она плотнее прилегала к краю
            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "VIEW MORE",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.Gray
                )
            }
        }
    }
}