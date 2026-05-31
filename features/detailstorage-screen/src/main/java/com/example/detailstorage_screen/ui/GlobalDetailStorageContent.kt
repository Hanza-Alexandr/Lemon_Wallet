package com.example.detailstorage_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.detailstorage_screen.model.UIStatesDetailStorage
import com.example.domain.Currency
import com.example.domain.TypeStorage
import com.example.domain.domainmodel.DomainColor
import com.example.ui.colorpikerrow.ColorPickerRow
import com.example.ui.colorpikerrow.ColorPickerRowViewModel
import com.example.ui.shimmerEffect


@Composable
fun GlobalDetailStorageContent(
    modifier: Modifier = Modifier,
    uiState: UIStatesDetailStorage,
    onNameChange: (String) -> Unit,
    onNoteChange: (String) -> Unit,
    onColorChange: (DomainColor?) -> Unit,
    onTypeChange: (TypeStorage) -> Unit,
    onCurrencyChange: (Currency) -> Unit,
) {
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
            modifier = modifier.fillMaxWidth(),
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
            modifier = modifier.fillMaxWidth(),
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
            modifier = modifier.fillMaxWidth(),
        )

        StorageExposedDropdown(
            label = "Валюта",
            options = Currency.entries,
            selectedOption = uiState.currency,
            color = if (uiState.currency == null) Color.Red else Color.Gray,
            onOptionSelected = onCurrencyChange,
            modifier = modifier.fillMaxWidth()
        )
        ColorPickerRow(
            modifier = modifier,
            selectedColor = uiState.color,
            onColorSelected = onColorChange
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
