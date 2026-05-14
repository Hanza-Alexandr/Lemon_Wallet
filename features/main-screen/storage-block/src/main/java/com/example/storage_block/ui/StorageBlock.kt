package com.example.storage_block.ui

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.Currency
import com.example.domain.EnumColor
import com.example.domain.Storage
import com.example.domain.SystemColor
import com.example.domain.TypeStorage
import com.example.storage_block.model.storagesLOCALTESTDATA
import com.example.storage_block.ui.components.StorageList
import com.example.storage_block.ui.components.blocks.common.TemplateMainsBlock
import com.example.storage_block.ui.components.blocks.common.TopBarStorageBlock
import com.example.ui.BottomBarStorageBlock

@Preview
@Composable
fun StorageBlockPreview(){
    StorageBlock(
        selectedList = setOf(0,1),
        storages = storagesLOCALTESTDATA,
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