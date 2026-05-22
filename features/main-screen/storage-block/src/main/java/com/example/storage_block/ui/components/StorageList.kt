package com.example.storage_block.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.storage_block.model.UiForStorageBlock
import com.example.storage_block.ui.components.blocks.components.storages.components.AccountHorizontalAddCard
import com.example.storage_block.ui.components.blocks.components.storages.components.StorageHorizontalCard

@Composable
fun StorageList(
    pagerState: PagerState,
    storages: List<UiForStorageBlock>,
    itemsPerPage: Int,
    elementHeight: Int = 65, //Высота плиток, для того что бы плашка карты, плашка карты добавления и пустые элементы были одной высоты
    onEditStorageClick: (storageId: String) -> Unit,
    onStorageClick: (id: String) -> Unit,
    onStorageLongClick: (id: String) -> Unit,
    onAddStorageClick: () -> Unit,
    ){

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 6.dp, horizontal = 12.dp),// Отступ всего контента от контейнера Horizontal pager
        //pageSpacing = 12.dp, //Расстояние между страницами
    ) { pageIndex ->
        Column (
            verticalArrangement = Arrangement.spacedBy(4.dp) //Отступы между элементами
        ) {
            // Вычисляем элементы для текущей страницы
            for (i in 0 until itemsPerPage) {
                val itemIndex = pageIndex * itemsPerPage + i
                Box {
                    when {
                        itemIndex < storages.size -> {
                            StorageHorizontalCard(
                                isSelected = storages[itemIndex].isSelected,
                                isSelectedMode = storages.count { it.isSelected } > 1,
                                cardHeight = elementHeight,
                                storage = storages[itemIndex],
                                onEditStorageClick = onEditStorageClick,
                                onStorageClick = {
                                    onStorageClick(storages[itemIndex].storage.id)
                                },
                                onStorageLongClick = {
                                    onStorageLongClick(storages[itemIndex].storage.id)
                                }
                            )
                        }
                        itemIndex == storages.size -> {
                            AccountHorizontalAddCard(
                                elementHeight,
                                onAddStorageClick = onAddStorageClick
                            )
                        }
                        else -> {
                            Spacer(Modifier.heightIn(elementHeight.dp))
                            // Пустое место для выравнивания сетки на последней странице

                        }
                    }
                }
            }
        }
    }
}
