package com.example.last_operation_block.ui.components.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OperationBaseItem(
    modifier: Modifier = Modifier,
    iconBlock: @Composable () -> Unit,
    contentBlock: @Composable ColumnScope.() -> Unit,
    actionBlock: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp), // Внешние отступы
        shape = RoundedCornerShape(12.dp),
        color = Color.White // Или MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp), // Внутренние отступы
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Левая часть: Иконка
            iconBlock()

            Spacer(modifier = Modifier.width(16.dp))

            // Центральная часть: Текст (динамически расширяется)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                contentBlock()
            }

            // Правая часть: Сумма или другие действия (если есть)
            actionBlock?.let {
                Spacer(modifier = Modifier.width(8.dp))
                it()
            }
        }
    }
}