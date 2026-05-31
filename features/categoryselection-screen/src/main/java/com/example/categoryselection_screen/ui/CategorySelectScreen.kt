package com.example.categoryselection_screen.ui

import CategoryItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ui.GlobalTopBar

@Composable
fun CategorySelectScreen(viewModel: CategorySelectViewModel = hiltViewModel()){

    val categories by viewModel.allCategories.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            TopBarCategorySelectScreen()

            categories?.forEach { category ->
                CategoryItem(
                    category = category,
                    onClick = {

                    },
                )
            }

        }
    }
}

/**
 * Нажимаю на дополнительные категории
 * Снизу выползает шторка выбора категорий
 * Первоначально список глобальных категорий
 * Начимаю на категорию шторка опускается. Выбранная категория становиться выбранной в другом окне
 * Нажимаю на стрлку у категории -> шторка с категориями обновляется появляються вложенные категории. В самом вреху отдельно висит родительская категория.
 * Системной навигацией управляется вложенность категорий
 */


@Composable
fun TopBarCategorySelectScreen(){
    GlobalTopBar {

    }
}