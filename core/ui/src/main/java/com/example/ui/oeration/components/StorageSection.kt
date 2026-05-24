package com.example.ui.oeration.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.domainmodel.DomainStorage

// Модель данных для счета
data class AccountUiModel(
    val id: String,
    val name: String,
    val balance: String,
    val isSelected: Boolean = false
)

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun AccountsSectionPreview() {
    val accounts = listOf(
        AccountUiModel("1", "Сбер", "1232,12p"),
        AccountUiModel("2", "Наличка", "5 452,00p", isSelected = true)
    )
    AccountsSection(
        modifier = Modifier.padding(16.dp),
        storages =accounts,
        onSelected = {}
    )
}
@Composable
fun AccountsSection(
    modifier: Modifier = Modifier,
    storages: List<AccountUiModel>,
    onSelected: (DomainStorage) -> Unit
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
                AccountItem(storage)
            }

            // Кнопка добавления нового счета
            item {
                AddAccountButton()
            }
        }
    }
}

@Composable
fun AccountItem(account: AccountUiModel) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (account.isSelected) Color(0xFFDDE1E9) else Color.White,
        shadowElevation = if (account.isSelected) 0.dp else 2.dp,
        modifier = Modifier.width(130.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = account.name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = account.balance,
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

