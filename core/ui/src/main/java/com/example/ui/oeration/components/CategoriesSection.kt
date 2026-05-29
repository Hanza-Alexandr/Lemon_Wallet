package com.example.ui.oeration.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.domainmodel.DomainCategory
import com.example.ui.colorpikerrow.toColor
import javax.inject.Inject

// Модель данных для категории
data class UiModelCategory(
    val category: DomainCategory,
    val isSelect: Boolean,
)

class ConvertDomainCategoryToUiModel @Inject constructor(){
    suspend operator fun invoke(category: DomainCategory): UiModelCategory {
        return UiModelCategory(
            category = category,
            isSelect = false
        )
    }
}

@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    topCategories: List<UiModelCategory>, //TODO нужен useCase для выдачи топа категорий
    onCategorySelected: (UiModelCategory) -> Unit,
    onMoreCategory: ()-> Unit
) {
    // Тестовые данные (на скриншоте 3 одинаковых категории и кнопка "еще")

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Частые категории:",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D2126)
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(topCategories) { category ->
                CategoryItem(category, onCategorySelected)
            }

            // Кнопка "Еще" (...)
            item {
                MoreCategoryItem(onMoreCategory)
            }
        }
    }
}

@Composable
fun CategoryItem(
    categoryUIModel: UiModelCategory,
    onCategorySelected: (UiModelCategory) -> Unit
)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(72.dp)
            .clickable(onClick = {
                onCategorySelected(categoryUIModel)
            }
            ),
    ) {
        // Круглый плейсхолдер для иконки
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .then(
                    if (categoryUIModel.isSelect) {
                        Modifier.border(
                            width = 3.dp,
                            color = Color(0xFF1D2126), // Темный цвет для акцента
                            shape = CircleShape
                        )
                    } else {
                        Modifier
                    }
                )
                .background(categoryUIModel.category.color?.toColor() ?: Color.White),
            contentAlignment = Alignment.Center
        ) {
            // Здесь будет иконка категории
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = categoryUIModel.category.name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                lineHeight = 14.sp,
                color = Color(0xFF9AA4B2)
            ),
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun MoreCategoryItem(onMoreCategory: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp).clickable(onClick = onMoreCategory)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "More",
                tint = Color(0xFF1D2126)
            )
        }
        // Пустой текст или отступ под кнопкой "еще" для выравнивания
        Spacer(modifier = Modifier.height(8.dp))
    }
}
