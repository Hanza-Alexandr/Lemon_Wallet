package com.example.ui.oeration.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.domainmodel.DomainCategory

// Модель данных для категории
data class CategoryUiModel(
    val id: String,
    val name: String,
    val color: Color = Color(0xFFE0E0E0) // Светло-серый плейсхолдер
)

@Preview
@Composable
private fun CategorySectionPreview() {
    val categories = listOf(
        CategoryUiModel("1", "Еда и\nнапитки"),
        CategoryUiModel("2", "Еда и\nнапитки"),
        CategoryUiModel("3", "Еда и\nнапитки"),
        CategoryUiModel("4", "Еда и\nнапитки"),
        CategoryUiModel("5", "Еда и\nнапитки"),
    )
    CategoriesSection(
        topCategories = categories,
        onSelect = {}
    )
}

@Composable
fun CategoriesSection(
    modifier: Modifier = Modifier,
    topCategories: List<CategoryUiModel>, //TODO нужен useCase для выдачи топа категорий
    onSelect: (DomainCategory) -> Unit
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
                CategoryItem(category)
            }

            // Кнопка "Еще" (...)
            item {
                MoreCategoryItem()
            }
        }
    }
}

@Composable
fun CategoryItem(category: CategoryUiModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        // Круглый плейсхолдер для иконки
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(category.color),
            contentAlignment = Alignment.Center
        ) {
            // Здесь будет иконка категории
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
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
fun MoreCategoryItem() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
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
