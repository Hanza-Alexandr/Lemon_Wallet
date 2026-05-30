package com.example.detailoperation_screen.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.CreditOperation
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.TransferOperation
import kotlin.reflect.KClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionTypeSelector(
    modifier: Modifier = Modifier,
    selectIndex: Int,
    onChangeType: (KClass<out DomainOperation>) -> Unit
) {

    // Состояние выбранного индекса (0 - Доход, 1 - Расход, 2 - Перевод)
    var selectedIndex by remember { mutableIntStateOf(selectIndex) }
    val options = listOf("Доход", "Расход", "Перевод")

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = {
                    onChangeType(
                        when (index) {
                            0 -> DebitOperation::class
                            1 -> CreditOperation::class
                            2 -> TransferOperation::class
                            else -> throw IllegalArgumentException("Invalid index")
                        }
                    )
                    selectedIndex = index },
                selected = index == selectedIndex,
                icon = {
                    // Показываем галочку только у выбранного элемента
                    SegmentedButtonDefaults.Icon(active = index == selectedIndex) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            modifier = Modifier.size(SegmentedButtonDefaults.IconSize)
                        )
                    }
                },
                colors = SegmentedButtonDefaults.colors(
                    // Настройка цветов под макет
                    activeContainerColor = Color(0xFFE6E0F9), // Светло-фиолетовый фон
                    activeContentColor = Color(0xFF6750A4),   // Фиолетовый текст/иконка
                    inactiveContainerColor = Color.Transparent,
                    inactiveContentColor = Color(0xFF49454F)  // Серый текст
                )
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal
                    )
                )
            }
        }
    }
}