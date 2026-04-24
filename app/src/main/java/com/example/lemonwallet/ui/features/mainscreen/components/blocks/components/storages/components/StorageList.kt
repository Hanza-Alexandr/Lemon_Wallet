package com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.storages.components

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
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.model.domain.TypeStorage

@Preview
@Composable
fun StorageListPreview(){
    StorageList(
        selectedList = setOf(0,1),
        pagerState = rememberPagerState(pageCount = { 2 }),
        storages = listOf(
            Storage.create(
                id = 1,
                name = "Sber",
                userId = -1,
                currency = Currency.RUB,
                typeStorage = TypeStorage.BANK_ACCOUNT,
                note = null,
                color = SystemColor(1, "#FF5733"),
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
                color = SystemColor(1, "#FF5733"),
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
                color = SystemColor(1, "#FF5733"),
                isStatistics = true,
                isArchive = false
            ),
        ),
        itemsPerPage = 3,
        onEditStorageClick = {  },
        onStorageClick = {  },
        onAddStorageClick = {  },
        onStorageLongClick = { }
    )
}

@Composable
fun StorageList(
    selectedList: Set<Int>,
    pagerState: PagerState,
    storages: List<Storage>,
    itemsPerPage: Int,
    elementHeight: Int = 65, //Высота плиток, для того что бы плашка карты, плашка карты добавления и пустые элементы были одной высоты
    onEditStorageClick: (storageId: Long) -> Unit,
    onStorageClick: (index: Int) -> Unit,
    onStorageLongClick: (index: Int) -> Unit,
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
                Box() {
                    when {
                        itemIndex <storages.size -> {
                            val isSelected = selectedList.contains(itemIndex)
                            StorageHorizontalCard(
                                isSelected = isSelected,
                                isSelectedMode = selectedList.size > 1,
                                cardHeight = elementHeight,
                                storage = storages[itemIndex],
                                onEditStorageClick = onEditStorageClick,
                                onStorageClick = {
                                    onStorageClick(itemIndex)
                                },
                                onStorageLongClick = {
                                    onStorageLongClick(itemIndex)
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


