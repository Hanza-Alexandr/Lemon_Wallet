package com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.storages

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.ui.features.mainscreen.components.blocks.common.BottomBarStorageBlock
import com.example.lemonwallet.ui.features.mainscreen.components.blocks.common.TemplateMainsBlock
import com.example.lemonwallet.ui.features.mainscreen.components.blocks.common.TopBarStorageBlock
import com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.storages.components.StorageList

@Preview
@Composable
fun StorageBlockPreview(){
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
            color = SystemColor(1, EnumColor.ORANGE.hexCode),
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
            color = SystemColor(1, EnumColor.GREEN.hexCode),
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
            color = SystemColor(1, EnumColor.BLUE.hexCode),
            isStatistics = true,
            isArchive = false
        ),
    ),
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
    onEditStorageClick: (storageId: Long) -> Unit,
    onStorageClick: (index: Int) -> Unit,
    onStorageLongClick: (index: Int) -> Unit,
    onAddStorageClick: () -> Unit
) {

    val itemsPerPage = 3 // Количество элементов на одной странице
    val totalItemsCount = storages.size + 1 // Добавляем один виртуальный элемент для кнопки "Добавить"
    val pageCount = (totalItemsCount + itemsPerPage - 1) / itemsPerPage // Рассчитываем количество страниц
    val pagerState = rememberPagerState(pageCount = { pageCount })

    TemplateMainsBlock(
        topBar = ::TopBarStorageBlock,
        bottomBar = ::BottomBarStorageBlock
    ) {
        StorageList(
            selectedList = selectedList,
            pagerState = pagerState,
            storages = storages,
            itemsPerPage = itemsPerPage,
            onStorageClick = onStorageClick,
            onEditStorageClick = onEditStorageClick,
            onAddStorageClick = onAddStorageClick,
            onStorageLongClick = onStorageLongClick
        )
    }
}





