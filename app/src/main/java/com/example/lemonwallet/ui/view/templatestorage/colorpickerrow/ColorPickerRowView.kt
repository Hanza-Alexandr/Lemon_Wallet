package com.example.lemonwallet.ui.view.templatestorage.colorpickerrow

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.model.domain.ExistColor
import androidx.core.graphics.toColorInt
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.ui.theme.MainDark
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
        onColorSelected = {},
        onAddNewColorClick = {}
    )
}

@Composable
fun ColorPickerRow(
    availableColors: List<ColorUIState>,
    selectedColor: ColorUIState?,
    onColorSelected: (ColorUIState?) -> Unit,
    onAddNewColorClick: () -> Unit
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
                onColorSelected(null)
            }
        }

        // 2. Цвета из БД
        items(availableColors) { existColor ->
            ColorCircle(
                color = existColor.toColor(),
                isSelected = selectedColor == existColor,
                onColorSelectClick = { onColorSelected(existColor) }
            )
        }
        // 3. Последний элемент - Создание цвета
        item {
           AddColorCircle { onAddNewColorClick() }
        }
    }
}

@Preview
@Composable
fun ColorCirclePreview() {
    ColorCircle(
        color = Color.Red,
        isSelected = true,
        onColorSelectClick = {}
    )
}
@Composable
fun ColorCircle(
    color: Color?,
    isSelected: Boolean,
    onColorSelectClick: () -> Unit
) {
    val borderColor = if (isSelected) Color.Black else Color.Transparent
    val backgroundColor = color?: Color.Transparent

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .padding(4.dp) // Отступ для эффекта рамки вокруг цвета
            .clip(CircleShape)
            .background(if (color == null) Color.Gray.copy(alpha = 0.1f) else backgroundColor)
            .clickable { onColorSelectClick() },
        contentAlignment = Alignment.Center
    ) {
        if (color == null) {
            Icon(
                imageVector = Icons.Default.Block,
                contentDescription = "Без цвета",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
@Preview
@Composable
fun EmptyColorCirclePreview() {
    EmptyColorCircle(
        isSelected = false,
        onSetNoColorClick = {}
    )
}

@Composable
fun EmptyColorCircle(
    isSelected: Boolean,
    onSetNoColorClick: () -> Unit
) {
    val borderColor = if (isSelected) Color.White else Color.Transparent

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .padding(4.dp) // Отступ для эффекта рамки вокруг цвета
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.1f))
            .clickable { onSetNoColorClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Block,
            contentDescription = "Без цвета",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )

    }
}

@Preview
@Composable
fun AddColorCirclePreview() {
    AddColorCircle(
        onAddColorClick = {}
    )
}

@Composable
fun AddColorCircle(
    onAddColorClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.2f))
            .clickable { onAddColorClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Добавить цвет",
            tint = Color.White
        )
    }
}