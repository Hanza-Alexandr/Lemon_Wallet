package com.example.last_operation_block.ui.components.items

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.GeneralOperation
import com.example.last_operation_block.ui.components.TestData
import com.example.ui.them.SecondDark



@Composable
fun GeneralTransaction(generalTransaction: GeneralOperation) {
    OperationBaseItem(
        iconBlock = {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White, CircleShape), //TODO
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = generalTransaction.category.icon.toInt()),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.Black // Иконки на скрине черные
                )
            }
        },
        contentBlock = {
            // Заголовок: Еда и напитки
            Text(
                text = generalTransaction.category.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = SecondDark
            )

            // Подзаголовок 1: Счет (Сбер)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AttachMoney, // Или ваша иконка кошелька
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = SecondDark
                )
                Text(
                    text = generalTransaction.storage.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = SecondDark
                )
            }

            // Подзаголовок 2: Заметка (если есть)
            if (generalTransaction.comment != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = SecondDark
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = generalTransaction.comment!!,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = SecondDark
                    )
                }
            }
        },
        actionBlock = {
            // Сумма: -2399р
            Text(
                text = when(generalTransaction){
                    is DebitOperation -> "+${generalTransaction.amount}"
                    is CreditOperation -> "-${generalTransaction.amount}"
                    else -> ""
                },
                style = MaterialTheme.typography.titleLarge,
                color = when(generalTransaction){
                    is DebitOperation -> Color(0xFF81C784)
                    is CreditOperation -> Color(0xFFE57373)
                    else -> Color(0xFFE57373)
                }
            )
        }
    )
}