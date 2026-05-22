package com.example.ui.storage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.NewDomainColor
import com.example.ui.StorageExposedDropdown
import com.example.ui.colorpikerrow.AddColorDialog
import com.example.ui.colorpikerrow.ColorPickerRow

@Composable
fun GlobalDetailStorageContent(
    uiState: UIStatesDetailStorage,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onTypeChange: (TypeStorage) -> Unit,
    onCurrencyChange: (Currency) -> Unit,
) {

    Column(modifier = modifier) {
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
                    value = uiState.name?:"",
                    onValueChange = onNameChange,
                    label = { Text("Название счета", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors =
                        if (uiState.name == null){
                            OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Red,
                                unfocusedLabelColor = Color.Red
                            )
                        }
                    else{
                            OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray
                            )
                        }
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

                StorageExposedDropdown(
                    label = "Тип счета",
                    options = TypeStorage.entries,
                    selectedOption = uiState.typeStorage,
                    color = if (uiState.typeStorage == null) Color.Red else Color.Gray,
                    onOptionSelected = onTypeChange,
                    modifier = Modifier.fillMaxWidth(),
                )

                StorageExposedDropdown(
                    label = "Валюта",
                    options = Currency.entries,
                    selectedOption = uiState.currency,
                    color = if (uiState.currency == null) Color.Red else Color.Gray,
                    onOptionSelected = onCurrencyChange,
                    modifier = Modifier.fillMaxWidth()
                )

                if (uiState.error != null) {
                    Text(
                        text = uiState.error,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
