package com.example.ui.colorpikerrow

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun ColorPickerRowPreview() {
    val availableColors = listOf(
        ColorUIState.LocalSystemColor(EnumColor.BLUE) ,
        ColorUIState.DataBaseColor(SystemColor(1, "#FF0000"))

    )
    ColorPickerRow(
        availableColors = availableColors,
        selectedColor = ColorUIState.LocalSystemColor(EnumColor.BLUE) ,
        isDeleteMode = true,
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
                val canBeDeleted = when(colorUiState){
                    is ColorUIState.LocalSystemColor -> false
                    is ColorUIState.DataBaseColor -> colorUiState.color is UserColor
                }

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
