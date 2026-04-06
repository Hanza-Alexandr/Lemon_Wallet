package com.example.lemonwallet.ui.view.mainscreen.storageblock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.ui.state.StorageBlockUiState
import com.example.lemonwallet.ui.theme.MainLight
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.BottomBarStorageBlock
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.PagesPartStorageBlock
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.TopBarStorageBlock
import com.example.lemonwallet.viewmodel.MainViewModel


/**
 * Основной компонент блока счетов с пагинацией
 */
@Composable
fun StorageBlock(
    storages: List<Storage>,
    storageBlockUIState: StorageBlockUiState,
    roundedCornerShapeBlock: Dp,
    onEditStorageClick: (index: Int) -> Unit,
    onStorageClick: (index: Int) -> Unit,
    onAddStorageClick: () -> Unit
) {

    val itemsPerPage = 3 // Количество элементов на одной странице
    val totalItemsCount = storages.size + 1 // Добавляем один виртуальный элемент для кнопки "Добавить"
    val pageCount = (totalItemsCount + itemsPerPage - 1) / itemsPerPage // Рассчитываем количество страниц
    val pagerState = rememberPagerState(pageCount = { pageCount })

    val contentHorizontalPadding = 8.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundedCornerShapeBlock))
            .background(MainLight) // Light Gray
            .padding(vertical = 8.dp,)
    ) {
        TopBarStorageBlock(contentHorizontalPadding)
        PagesPartStorageBlock(
            storageBlockUiState = storageBlockUIState,
            pagerState = pagerState,
            storages = storages,
            itemsPerPage = itemsPerPage,
            onStorageClick = onStorageClick,
            onEditStorageClick = onEditStorageClick,
            onAddStorageClick = onAddStorageClick
        )
        BottomBarStorageBlock(contentHorizontalPadding)
    }
}





