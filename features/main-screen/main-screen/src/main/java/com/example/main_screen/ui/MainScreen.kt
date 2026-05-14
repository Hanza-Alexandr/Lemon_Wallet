package com.example.main_screen.ui

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
import androidx.lifecycle.ViewModel
import com.example.ui.them.MainDark

@Composable
fun MainScreen(blockList: List<@Composable () -> Unit> = emptyList()){
    MainScreenContent(
        onEditStorageClick = {
            TODO()
        },
        onCreateStorageClick = {
            TODO()
        },
        blockList = blockList
    )
}

@Composable
fun MainScreenContent(
    onEditStorageClick: (storageId: Long)-> Unit,
    onCreateStorageClick: ()-> Unit,
    blockList: List<@Composable ()-> Unit>
){

    var sizeMainButton by remember { mutableStateOf(0.dp) } //Размеры главной кнопки для нижнего отсупа
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark),
    ) {
        TopBarMainScreen(
            onMore = {
                TODO("Not Implement")
            }
        )

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
                /**
                 * В теории вызов каждого блока на главном экране
                 */
                blockList.forEach { it ->
                    it.invoke()
                }
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
                    TODO()
                }
            )
        }
    }
}