package com.example.cach_screen.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cach_screen.domain.model.Purchase

@Preview
@Composable
fun CachScreen(viewModel: CachScreenViewModel = hiltViewModel()){

    val list by viewModel.list.collectAsStateWithLifecycle()
    val uiState by viewModel.syncStatus.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    LaunchedEffect(uiState) {
        when(uiState){
            is UiSate.Error -> {
                ToastManager.showToast(context, (uiState as UiSate.Error).message)
            }
            is UiSate.Loading -> {
                ToastManager.showToast(context, "Загрузка")
            }
            is UiSate.Syncing -> {
                ToastManager.showToast(context, "Синхронизация")
            }
            is UiSate.Success -> {
                ToastManager.showToast(context, "Синхронизация успешна")
            }
            else -> {}
        }
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
fun HistrotyCardItem(card: Purchase){
    Column(Modifier.fillMaxWidth()) {
        Text("ID: ${card.id}")
        Text("Дата покупки: ${card.dataBuy}")
        Text("Список покупок")
    }
}


object ToastManager {
    private var currentToast: Toast? = null

    fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        // Если уже есть висящий тост, отменяем его
        currentToast?.cancel()

        // Создаем новый тост и сохраняем ссылку на него
        currentToast = Toast.makeText(context.applicationContext, message, duration)

        // Показываем
        currentToast?.show()
    }
}