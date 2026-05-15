package com.example.last_operation_block.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.them.MainDark
import com.example.ui.them.SecondDark

@Preview
@Composable
fun EmptyOperations() {
    OperationBaseItem(
        iconBlock = {
            // Атомарный круг с эмодзи
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0xFFFFE599), // Желтоватый фон со скрина
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🤷‍♂️", fontSize = 24.sp) // Эмодзи
            }
        },
        contentBlock = {
            Text(
                text = "Not Operations",
                style = MaterialTheme.typography.titleMedium,
                color = SecondDark
            )
        }
    )
}