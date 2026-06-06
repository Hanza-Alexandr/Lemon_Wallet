package com.example.ui.selectcategory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategorySelectItem(
    id: String,
    name: String,
    icon: ImageVector,
    isSelected: Boolean = false,        // Состояние 2 (Выбранная)
    hasSubCategories: Boolean = false,  // Состояние 3 (С дочерними)
    isParentHeader: Boolean = false,    // Состояние 4 (Выделенная/Родитель)
    onItemClick: () -> Unit,
    onArrowClick: ((id: String) -> Unit)? = null  // Клик конкретно по стрелке (если нужно)
) {
    // Определяем цвета и границы в зависимости от состояния
    val borderColor = when {
        isSelected -> Color(0xFF9191FF) // Светло-фиолетовый для выбранного
        isParentHeader -> Color(0xFF636363) // Темно-серый для "активного родителя"
        else -> Color.Transparent
    }

    val borderWidth = if (isSelected || isParentHeader) 2.dp else 0.dp
    val backgroundColor = Color(0xFFE0E0E0) // Светло-серый фон

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor,
        border = if (borderWidth > 0.dp) BorderStroke(borderWidth, borderColor) else null
    ) {
        Row(
            modifier = Modifier
                .clickable { onItemClick() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка категории (Круг на вашем скриншоте)
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color.Gray // Можно заменить на иконку
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Название категории
            Text(
                text = name,
                modifier = Modifier.weight(1f),
                fontSize = 18.sp,
                fontWeight = if (isParentHeader) FontWeight.Bold else FontWeight.Medium,
                color = Color.Black
            )

            // Правая часть (Иконки состояния)
            when {
                isSelected -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color(0xFF9191FF)
                    )
                }
                hasSubCategories -> {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Has subcategories",
                        modifier = Modifier.clickable { onArrowClick?.invoke(id) ?: onItemClick() },
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategorySelectItemPreview(){
    Column() {
        CategorySelectItem(id = "1", name = "Категория", icon = Icons.Default.Category, onItemClick = {})
        CategorySelectItem(
            id = "1",
            name = "Категория",
            icon = Icons.Default.Category,
            isSelected = true,
            onItemClick = {}
        )
        CategorySelectItem(
            id = "1",
            name = "Категория",
            icon = Icons.Default.Category,
            hasSubCategories = true,
            onItemClick = { /* Открыть подкатегории */ }
        )
        CategorySelectItem(
            id = "1",
            name = "Родительская категория",
            icon = Icons.Default.Category,
            isParentHeader = true,
            onItemClick = { /* Назад или выбор родителя */ }
        )
    }


}