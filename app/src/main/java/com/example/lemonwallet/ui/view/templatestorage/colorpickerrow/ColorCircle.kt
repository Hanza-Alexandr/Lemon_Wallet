package com.example.lemonwallet.ui.view.templatestorage.colorpickerrow

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorCircle(
    color: Color?,
    isSelected: Boolean,
    isDeleteMode: Boolean = false,
    canBeDeleted: Boolean = false,
    onColorSelectClick: () -> Unit,
    onLongClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val borderColor = if (isSelected) Color.Black.copy(alpha = 0.5f) else Color.Transparent
    val backgroundColor = color ?: Color.Transparent

    val infiniteTransition = rememberInfiniteTransition(label = "shake")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier.size(56.dp) // Increased size to accommodate delete icon
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
                .graphicsLayer {
                    if (isDeleteMode && canBeDeleted) {
                        rotationZ = rotation
                    }
                }
                .clip(CircleShape)
                .border(2.dp, borderColor, CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
                .background(if (color == null) Color.Gray.copy(alpha = 0.1f) else backgroundColor)
                .combinedClickable(
                    onClick = { if (!isDeleteMode) onColorSelectClick() },
                    onLongClick = onLongClick
                ),
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

        if (isDeleteMode && canBeDeleted) {
            Surface(
                modifier = Modifier
                    .size(20.dp)
                    .offset(x = (-2).dp, y = 2.dp)
                    .clickable { onDeleteClick() },
                shape = CircleShape,
                color = Color.Red,
                shadowElevation = 2.dp
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Удалить",
                    tint = Color.White,
                    modifier = Modifier.padding(2.dp)
                )
            }
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

@Preview
@Composable
fun ColorCircleDeleteModePreview() {
    ColorCircle(
        color = Color.Red,
        isSelected = false,
        isDeleteMode = true,
        canBeDeleted = true,
        onColorSelectClick = {}
    )
}

@Composable
fun EmptyColorCircle(
    isSelected: Boolean,
    onSetNoColorClick: () -> Unit
) {
    val borderColor = if (isSelected) Color.Black.copy(alpha = 0.5f) else Color.Transparent

    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .padding(4.dp)
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
