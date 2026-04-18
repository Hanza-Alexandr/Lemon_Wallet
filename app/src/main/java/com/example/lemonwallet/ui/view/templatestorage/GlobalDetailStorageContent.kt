package com.example.lemonwallet.ui.view.templatestorage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonwallet.R
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.ui.view.state.EditStorageUiState
import com.example.lemonwallet.ui.view.state.GlobalStorageUiState

@Preview
@Composable
fun GlobalDetailStorageContentPreview() {
    GlobalDetailStorageContent(
        uiState = EditStorageUiState(
            name = "Наличные",
            note = "В кошельке",
            isLoading = false,
            isStatistics = true,
            isArchive = false,
        ),
        onNameChange = {},
        onNoteChange = {},
        onTypeChange = {},
        onCurrencyChange = {},
        onColorChange = {},
        onStatisticsChange = {},
        onArchiveChange = {}
    )
}
@Composable
fun GlobalDetailStorageContent(
    uiState: GlobalStorageUiState,
    onNameChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onTypeChange: (TypeStorage) -> Unit,
    onCurrencyChange: (Currency) -> Unit,
    onColorChange: (ExistColor) -> Unit,
    onStatisticsChange: (Boolean) -> Unit,
    onArchiveChange: (Boolean) -> Unit,
) {
    Column{
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = onNameChange,
                    label = { Text("Название счета", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                OutlinedTextField(
                    value = uiState.note ?: "",
                    onValueChange = onNoteChange,
                    label = { Text("Заметка", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        enabled = false,
                        value = uiState.typeStorage.toString(),
                        onValueChange = {},
                        label = { Text("Тип счета", color = Color.Gray) },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .weight(1F),
                        colors = OutlinedTextFieldDefaults.colors(
                            // Настраиваем цвета для disabled состояния, чтобы они выглядели как обычные
                            disabledBorderColor = Color.Gray,
                            disabledLabelColor = Color.Gray,
                            disabledContainerColor = Color.Transparent,
                            // Убираем прозрачность, которую Material накладывает на disabled элементы
                            disabledPlaceholderColor = Color.Gray
                        )
                    )
                    IconButton(
                        onClick = {
                            TODO("Not implement")
                        }
                    ) {
                        Icon(painterResource(R.drawable.expand_more), contentDescription = "Save")
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        enabled = false,
                        value = uiState.currency.toString(),
                        onValueChange = {},
                        label = { Text("Валюта", color = Color.Gray) },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .weight(1F),
                        colors = OutlinedTextFieldDefaults.colors(
                            // Настраиваем цвета для disabled состояния, чтобы они выглядели как обычные
                            disabledBorderColor = Color.Gray,
                            disabledLabelColor = Color.Gray,
                            disabledContainerColor = Color.Transparent,
                            // Убираем прозрачность, которую Material накладывает на disabled элементы
                            disabledPlaceholderColor = Color.Gray
                        )
                    )
                    IconButton(
                        onClick = {
                            TODO("Not implement")
                        }
                    ) {
                        Icon(painterResource(R.drawable.expand_more), contentDescription = "Save")
                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1F),
                        shape = RoundedCornerShape(6.dp),
                        onClick = {}
                    ) {

                    }
                    IconButton(
                        onClick = {
                            TODO("Not implement")
                        }
                    ) {
                        Icon(painterResource(R.drawable.expand_more), contentDescription = "Save")
                    }

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Учитывать в статистике", fontSize = 16.sp)
                        Text("Данные будут влиять на общую статистику", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(
                        checked = uiState.isStatistics,
                        onCheckedChange = onStatisticsChange
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("В архиве", fontSize = 16.sp)
                        Text("Скрыть счет из основного списка", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(
                        checked = uiState.isArchive,
                        onCheckedChange = onArchiveChange
                    )
                }

                if (uiState.error != null) {
                    Text(text = uiState.error!!, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}