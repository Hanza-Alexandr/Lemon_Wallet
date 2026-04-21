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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.ui.theme.MainLight
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.BottomBarStorageBlock
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.PagesPartStorageBlock
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.TopBarStorageBlock

@Preview
@Composable
fun Test8(){
StorageBlock(
    selectedList = setOf(0,1),
    storages = listOf(
        Storage.create(
            id = 1,
            name = "Sber",
            userId = -1,
            currency = Currency.RUB,
            typeStorage = TypeStorage.BANK_ACCOUNT,
            note = null,
            color = SystemColor(1, EnumColor.ORANGE.toString()),
            isStatistics = true,
            isArchive = false
        ),
        Storage.create(
            id = 2,
            name = "Sber",
            userId = -1,
            currency = Currency.RUB,
            typeStorage = TypeStorage.BANK_ACCOUNT,
            note = null,
            color = SystemColor(1, EnumColor.GREEN.toString()),
            isStatistics = true,
            isArchive = false
        ),
        Storage.create(
            id = 3,
            name = "Sber",
            userId = -1,
            currency = Currency.RUB,
            typeStorage = TypeStorage.BANK_ACCOUNT,
            note = null,
            color = SystemColor(1, EnumColor.BLUE.toString()),
            isStatistics = true,
            isArchive = false
        ),
    ),
    roundedCornerShapeBlock = 22.dp,
    onEditStorageClick = { },
    onStorageClick = { },
    onStorageLongClick = { }
) { }
}
/**
 * Основной компонент блока счетов с пагинацией
 */
@Composable
fun StorageBlock(
    storages: List<Storage>,
    selectedList: Set<Int>,
    roundedCornerShapeBlock: Dp,
    onEditStorageClick: (storageId: Long) -> Unit,
    onStorageClick: (index: Int) -> Unit,
    onStorageLongClick: (index: Int) -> Unit,
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
            selectedList = selectedList,
            pagerState = pagerState,
            storages = storages,
            itemsPerPage = itemsPerPage,
            onStorageClick = onStorageClick,
            onEditStorageClick = onEditStorageClick,
            onAddStorageClick = onAddStorageClick,
            onStorageLongClick = onStorageLongClick
        )
        BottomBarStorageBlock(contentHorizontalPadding)
    }
}





