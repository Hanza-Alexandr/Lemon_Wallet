package com.example.lemonwallet.ui.view.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lemonwallet.ui.theme.MainDark
import com.example.lemonwallet.ui.view.mainscreen.cashflowgrafficsblock.CashFlowGraffias
import com.example.lemonwallet.ui.view.mainscreen.lastoperationsblock.LastOperations
import com.example.lemonwallet.ui.view.mainscreen.mainbutton.MainButton
import com.example.lemonwallet.ui.view.mainscreen.storageblock.StorageBlock
import com.example.lemonwallet.ui.view.mainscreen.topbar.TopBar
import com.example.lemonwallet.viewmodel.MainViewModel

@Composable
fun MainScreenView(
    vm: MainViewModel
){
    var sizeMainButton by remember { mutableStateOf(0.dp) } //Размеры главной кнопки для нижнего отсупа
    val density = LocalDensity.current
    val roundedCornerShapeBlock = 22.dp //Скругление блоков интерфейса
    val storages by vm.storageList.collectAsStateWithLifecycle()
    val storageBlocUIkState by vm.storageBlockState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark),
    ) {
        TopBar()
        Box{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = sizeMainButton)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StorageBlock(
                    storages = storages ?: listOf(),
                    storageBlockUIState = storageBlocUIkState,
                    roundedCornerShapeBlock = roundedCornerShapeBlock,
                    onStorageClick = { index ->
                        vm.switchSelect(index)
                    },
                    onEditStorageClick = {

                    },
                    onAddStorageClick = {

                    }
                )
                LastOperations()
                CashFlowGraffias()

            }
            MainButton(
                Modifier.align(Alignment.BottomEnd),
                onSizeGanged = { newSize ->
                    /**
                     * Нужно для отступа после всех элементов на экране, что бы блоки экрана не перекрывались главной кнопкой
                     * Система такая: при инициализации кнопки у нее вызывается метод onSizeChanged в параметры которого системой передается новый размер
                     * мы же туда от сюда передаем коллбек что бы тут зафиксировать размеры кнопки.
                     * эти размеры потом используются в padding выше
                     */
                    sizeMainButton = with(density) { newSize.height.toDp() }
                },
                onClick = {

                }
            )
        }
    }
}