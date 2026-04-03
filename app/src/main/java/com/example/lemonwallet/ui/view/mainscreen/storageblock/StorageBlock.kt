package com.example.lemonwallet.ui.view.mainscreen.storageblock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.BottomBarStorageBlock
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.TopBarStorageBlock
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.PagesPartStorageBlock
import com.example.lemonwallet.viewmodel.MainViewModel


/**
 * Основной компонент блока счетов с пагинацией
 */
@Composable
fun StorageBlock(
    vm: MainViewModel,
    storages: List<Storage>,
    roundedCornerShapeBlock: Dp,
    onAccountClick: (Boolean, Int) -> Unit,
    onAddAccountClick: () -> Unit
) {
    val itemsPerPage = 3
    // Добавляем один виртуальный элемент для кнопки "Добавить"
    val totalItemsCount = storages.size + 1
    // Рассчитываем количество страниц
    val pageCount = (totalItemsCount + itemsPerPage - 1) / itemsPerPage
    val pagerState = rememberPagerState(pageCount = { pageCount })

    val contentHorizontalPadding = 8.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(roundedCornerShapeBlock))
            .background(com.example.lemonwallet.ui.theme.MainLight) // Light Gray
            .padding(vertical = 8.dp,)
    ) {

        TopBarStorageBlock(contentHorizontalPadding)
        PagesPartStorageBlock(
            vm =vm,
            pagerState = pagerState,
            storages = storages,
            itemsPerPage = itemsPerPage,
            onAccountClick = { },
            onAddAccountClick = onAddAccountClick
        )
        //Spacer(modifier = Modifier.height(12.dp).fillMaxWidth())
        //PageIndicatorStorageBlock(pageCount, pagerState)
        BottomBarStorageBlock(contentHorizontalPadding)

    }
}





