package com.example.cach_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cach_screen.domain.PurchaseHistoryCardItem

@Preview
@Composable
fun CachScreen(viewModel: CachScreenViewModel = hiltViewModel()){

    val list by viewModel.list.collectAsStateWithLifecycle()
    val uiState by viewModel.syncStatus.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }



    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(if(isSystemInDarkTheme()) Color.Black else Color.White)) {
        Column(Modifier.fillMaxWidth()) {
            Button(onClick = viewModel::refresh, enabled = if(uiState is UiSate.Loading) false else true ) {
                Text("Sync")
            }
        }

        LazyColumn(contentPadding = PaddingValues(6.dp)) {
            items(
                items = list,
                key = {it.id}
            ){
                HistrotyCardItem(it)
            }
        }
    }
}

@Composable
fun HistrotyCardItem(card: PurchaseHistoryCardItem){
    Column(Modifier.fillMaxWidth()) {
        Text("ID: ${card.id}")
        Text("Дата покупки: ${card.dataBuy}")
        Text("Список покупок")
        LazyRow() {
            items(
                items = card.purchasesId,
                key = {it.id}
            ){
                Text(it.name)
            }
        }
    }
}
