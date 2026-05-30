package com.example.last_operation_block.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.South
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.domainmodel.TransferOperation
import com.example.domain.utils.toFormatAmount

@Composable
fun TransferOperationItem(
    transfer: TransferOperation,
    modifier: Modifier = Modifier,
    onClick: (TransferOperation)-> Unit,
) {
    // Контейнер карточки (белый фон, закругленные углы)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { onClick(transfer) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // ЛЕВАЯ ЧАСТЬ: Счета и стрелка
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Счет "Откуда"
            AccountLine(name = transfer.fromStorage.name)

            // Стрелка вниз (чуть смещена вправо для выравнивания с текстом)
            Icon(
                imageVector = Icons.Default.South,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp) // Смещение, чтобы стрелка была под текстом/иконкой
                    .size(14.dp),
                tint = Color.Gray
            )

            // Счет "Куда"
            AccountLine(name = transfer.toStorage.name)
        }

        // ПРАВАЯ ЧАСТЬ: Сумма
        Text(
            text = "${transfer.amount.toFormatAmount()}р", // Можно добавить форматирование валюты
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        )
    }
}

@Composable
private fun AccountLine(name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Иконка валюты или счета (как на скрине $)
        Text(
            text = "$",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1F1F1F)
        )
    }
}