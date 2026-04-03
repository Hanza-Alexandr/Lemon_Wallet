package com.example.lemonwallet.ui.view.mainscreen.storageblock.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.elements.horizontal.AccountHorizontalAddCard
import com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.elements.horizontal.AccountHorizontalCard
import com.example.lemonwallet.viewmodel.MainViewModel


@Composable
fun PagesPartStorageBlock(
    vm: MainViewModel,
    pagerState: PagerState,
    storages: List<Storage>,
    itemsPerPage: Int,
    onAccountClick: () -> Unit,
    onAddAccountClick: () -> Unit,
    elementHeight: Int = 65 //Высота плиток, для того что бы плашка карты, плашка карты добавления и пустые элементы были одной высоты
    ){

    val storageUiState by vm.uiState.collectAsState()

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
                            val currentStorage = storages[itemIndex]
                            val isSelected = storageUiState.isSelected(currentStorage.id)
                            val balance = vm.getStorageBalance(currentStorage)
                            AccountHorizontalCard(
                                isSelected = isSelected,
                                balance = balance,
                                elementHeight = elementHeight,
                                storage = storages[itemIndex],
                                onClick =  {
                                    vm.switchSelect(currentStorage.id)
                                    onAddAccountClick()
                                }
                            )
                        }
                        itemIndex == storages.size -> {
                            AccountHorizontalAddCard(elementHeight)
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


