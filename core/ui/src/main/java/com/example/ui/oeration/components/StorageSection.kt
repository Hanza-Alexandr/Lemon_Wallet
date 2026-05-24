package com.example.ui.oeration.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.usecase.GetStorageBalanceUseCase
import javax.inject.Inject

// Модель данных для счета
data class StorageUiModel(
    val storage: DomainStorage,
    val balance: String,
    val isSelected: Boolean = false
)

class ConvertDomainStorageToUiModel @Inject constructor(
    private val getStorageBalanceUseCase: GetStorageBalanceUseCase
){
    suspend operator fun invoke(storage: DomainStorage): StorageUiModel {
        return StorageUiModel(
            storage = storage,
            balance = getStorageBalanceUseCase.invoke(storage).toString()
        )
    }
}


@Composable
fun StorageSection(
    modifier: Modifier = Modifier,
    storages: List<StorageUiModel>,
    onStorageSelected: (StorageUiModel) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Счета:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D2126)
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(storages) { storage ->
                StorageItem(
                    storage,
                    onStorageSelected
                )
            }

            // Кнопка добавления нового счета
            item {
                AddAccountButton()
            }
        }
    }
}

@Composable
fun StorageItem(
    storage: StorageUiModel,
    onStorageSelected: (StorageUiModel) -> Unit)
{
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (storage.isSelected) Color(0xFFDDE1E9) else Color.White,
        shadowElevation = if (storage.isSelected) 0.dp else 2.dp,
        modifier = Modifier.width(130.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .clickable(
                    onClick = {
                        onStorageSelected(storage)
                    }
                )
        ) {
            Text(
                text = storage.storage.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = storage.balance,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF7F8A99),
                    fontSize = 12.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun AddAccountButton() {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.size(width = 56.dp, height = 56.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add account",
                tint = Color.Black
            )
        }
    }
}

