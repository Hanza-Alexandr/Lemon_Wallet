package com.example.storage_block.ui

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.storage_block.ui.StorageBlockViewModel
import com.example.storage_block.model.UiForStorageBlock
import com.example.storage_block.ui.components.StorageList
import com.example.storage_block.ui.components.blocks.common.TemplateMainsBlock
import com.example.storage_block.ui.components.blocks.common.TopBarStorageBlock
import com.example.ui.BottomBarStorageBlock

@Composable
fun StorageBlock(storageBlockViewModel: StorageBlockViewModel = hiltViewModel()){
    val storages by storageBlockViewModel.storageUIList.collectAsState()
    StorageBlockContent(
        storages = storages?:emptyList(),
        onStorageClick = storageBlockViewModel::onStorageClick,
        onStorageLongClick = storageBlockViewModel::onStorageLongClick,
        onEditStorageClick = storageBlockViewModel::onEditStorageClick,
        onAddStorageClick = storageBlockViewModel::onAddStorageClick
    )
}

/**
 * Основной компонент блока счетов с пагинацией
 */
@Composable
fun StorageBlockContent(
    storages: List<UiForStorageBlock>,
    onEditStorageClick: (storageId: String) -> Unit,
    onStorageClick: (id: String) -> Unit,
    onStorageLongClick: (id: String) -> Unit,
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
            pagerState = pagerState,
            storages = storages,
            itemsPerPage = itemsPerPage,
            onStorageClick = onStorageClick,
            onEditStorageClick = onEditStorageClick,
            onAddStorageClick = onAddStorageClick,
            onStorageLongClick = onStorageLongClick,
        )
    }
}