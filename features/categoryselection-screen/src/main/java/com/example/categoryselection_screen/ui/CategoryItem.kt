import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainColor

// Предполагаемая модель данных
data class UiCategory(
    val category: DomainCategory,
    val isSelected: Boolean,
    val hasSubcategories: Boolean
)

@Composable
fun CategoryItem(
    category: UiCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            // Добавляем обводку здесь
            .border(
                width = 2.dp,
                color = if (category.isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Круглый чекбокс (RadioButton style)
        Icon(
            imageVector = if (category.isSelected)
                Icons.Default.CheckCircle
            else
                Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (category.isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 2. Иконка категории в кружочке
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            if (category.category.icon.toIntOrNull() != null) {
                Icon(
                    painter = painterResource(id = category.category.icon.toInt()),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 3. Название категории
        Text(
            text = category.category.icon,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // 4. Стрелка (если есть подкатегории)
        if (category.hasSubcategories) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}


@Preview(showBackground = true, name = "Light Mode")
@Composable
fun CategoryItemPreview() {
    MaterialTheme {
        Surface {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                // Состояние: Выбрано, есть подкатегории
                CategoryItem(
                    UiCategory(
                        DomainCategory(
                            id = "1",
                            userId ="1",
                            name = "Еда",
                            color = DomainColor(
                                id = "1",
                                userId = "1",
                                hex = "#FFFFFF"
                            ),
                            icon = "ASDASD",
                            parenId = null
                        ),
                        isSelected = false,
                        hasSubcategories = true
                    ),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
                CategoryItem(
                    UiCategory(
                        DomainCategory(
                            id = "1",
                            userId ="1",
                            name = "Еда",
                            color = DomainColor(
                                id = "1",
                                userId = "1",
                                hex = "#FFFFFF"
                            ),
                            icon = "ASDASD",
                            parenId = null
                        ),
                        isSelected = false,
                        hasSubcategories = true
                    ),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
                CategoryItem(
                    UiCategory(
                        DomainCategory(
                            id = "1",
                            userId ="1",
                            name = "Еда",
                            color = DomainColor(
                                id = "1",
                                userId = "1",
                                hex = "#FFFFFF"
                            ),
                            icon = "ASDASD",
                            parenId = null
                        ),
                        isSelected = true,
                        hasSubcategories = true
                    ),
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}
 