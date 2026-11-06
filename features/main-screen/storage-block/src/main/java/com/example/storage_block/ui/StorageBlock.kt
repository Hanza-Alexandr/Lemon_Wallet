package com.example.storage_block.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.storage_block.ui.StorageBlockViewModel
import com.example.storage_block.model.UiForStorageBlock
import com.example.storage_block.ui.components.StorageList
import com.example.storage_block.ui.components.blocks.common.TemplateMainsBlock
import com.example.storage_block.ui.components.blocks.common.TopBarStorageBlock
import com.example.ui.BottomBarStorageBlock
import java.io.IOException


@Composable
fun StorageBlock(storageBlockViewModel: StorageBlockViewModel = hiltViewModel()){

    val storages by storageBlockViewModel.storageUIList.collectAsStateWithLifecycle()
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

    Column(modifier = Modifier.testTag("storage_block")) {
        // --- ВРЕМЕННАЯ КНОПКА ДЛЯ ТЕСТА CRASHLYTICS ---
        Button(onClick = { throw RuntimeException("Тестовый краш Crashlytics!") }) {
            Text("Скрашить приложение")
        }
        // ----------------------------------------------
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
        Column() {
            Button({throw NullPointerException()}) {
                Text("Fatal")
            }
            Button({
                try {
                    throw IOException()
                }
                catch (e: Exception){

                }
            }) {
                Text("NonFatal")
            }
        }
    }

}