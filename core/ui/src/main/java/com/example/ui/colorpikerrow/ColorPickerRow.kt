package com.example.ui.colorpikerrow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.domainmodel.DomainColor

@Composable
fun ColorPickerRow(
    modifier: Modifier = Modifier,
    viewModel: ColorPickerRowViewModel = hiltViewModel(),
    selectedColor: DomainColor?,
    isDeleteMode: Boolean = false,
    onColorSelected: (DomainColor?) -> Unit,
    onAddNewColorClick: () -> Unit = viewModel::onAddNewColorClick,
    onDeleteColor: (DomainColor) -> Unit = viewModel::onDeleteColor,
    onToggleDeleteMode: (Boolean) -> Unit = viewModel::onToggleDeleteMode
) {
    val availableColors by viewModel.availableColors.collectAsStateWithLifecycle(emptyList())
    val showAddDialog by viewModel.showDialog.collectAsStateWithLifecycle()
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
            modifier = modifier.fillMaxWidth(),
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
            items(availableColors?: emptyList()) { domainColor ->

                ColorCircle(
                    color = domainColor.toColor(),
                    isSelected = selectedColor?.toColor() == domainColor.toColor(),
                    isDeleteMode = isDeleteMode,
                    onColorSelectClick = { onColorSelected(domainColor) },
                    onLongClick = {
                         onToggleDeleteMode(true)
                    },
                    onDeleteClick = { onDeleteColor(domainColor) }
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
        if (showAddDialog) {
            AddColorDialog(
                availableColors = availableColors?: emptyList(),
                onDismiss = viewModel::hideAddDialog,
                onColorConfirmed = viewModel::createNewColor
            )
        }
    }
}

fun DomainColor.toColor(): Color {
    return try {
        // Убираем символ # если он есть, и парсим Long
        val colorString = hex.removePrefix("#")
        val colorLong = when (colorString.length) {
            6 -> "FF$colorString".toLong(16) // Добавляем альфа-канал, если его нет
            8 -> colorString.toLong(16)      // Используем как есть (AARRGGBB)
            else -> 0xFF000000               // Черный по умолчанию при ошибке длины
        }
        Color(colorLong)
    } catch (e: Exception) {
        Color.Gray // Фолбэк цвет в случае ошибки парсинга
    }
}
