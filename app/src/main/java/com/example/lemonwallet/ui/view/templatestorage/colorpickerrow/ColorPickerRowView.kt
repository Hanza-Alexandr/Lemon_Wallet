package com.example.lemonwallet.ui.view.templatestorage.colorpickerrow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.UserColor
import com.example.lemonwallet.ui.view.state.ColorUIState


@Preview
@Composable
fun ColorPickerRowPreview() {
    val availableColors = listOf(
        ColorUIState.LocalSystemColor(EnumColor.BLUE) ,
        ColorUIState.LocalSystemColor(EnumColor.GREEN)

    )
    ColorPickerRow(
        availableColors = availableColors,
        selectedColor = ColorUIState.LocalSystemColor(EnumColor.BLUE) ,
        isDeleteMode = false,
        onColorSelected = {},
        onAddNewColorClick = {},
        onDeleteColor = {},
        onToggleDeleteMode = {}
    )
}

@Composable
fun ColorPickerRow(
    availableColors: List<ColorUIState>,
    selectedColor: ColorUIState?,
    isDeleteMode: Boolean,
    onColorSelected: (ColorUIState?) -> Unit,
    onAddNewColorClick: () -> Unit,
    onDeleteColor: (ColorUIState) -> Unit,
    onToggleDeleteMode: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (isDeleteMode) onToggleDeleteMode(false)
            }
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Первый элемент - "Нет цвета"
            item {
                EmptyColorCircle(
                    isSelected = selectedColor == null,
                ) {
                    if (!isDeleteMode) onColorSelected(null)
                    else onToggleDeleteMode(false)
                }
            }

            // 2. Цвета из БД
            items(availableColors) { colorUiState ->
                val canBeDeleted = colorUiState is ColorUIState.DataBaseColor && colorUiState.color is UserColor
                
                ColorCircle(
                    color = colorUiState.toColor(),
                    isSelected = selectedColor?.toColor() == colorUiState.toColor(),
                    isDeleteMode = isDeleteMode,
                    canBeDeleted = canBeDeleted,
                    onColorSelectClick = { onColorSelected(colorUiState) },
                    onLongClick = {
                        if (canBeDeleted) onToggleDeleteMode(true)
                    },
                    onDeleteClick = { onDeleteColor(colorUiState) }
                )
            }
            // 3. Последний элемент - Создание цвета
            item {
                AddColorCircle { 
                    if (!isDeleteMode) onAddNewColorClick()
                    else onToggleDeleteMode(false)
                }
            }
        }
    }
}
