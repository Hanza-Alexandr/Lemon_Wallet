package com.example.storage_block.ui.components.blocks.components.storages.components

import androidx.compose.material.icons.Icons
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.Currency
import com.example.domain.Storage
import com.example.domain.SystemColor
import com.example.domain.TypeStorage
import com.example.storage_block.model.storageLOCALTESTDATA
import com.example.ui.them.*
import com.example.ui.R
import com.example.ui.toComposeColor

@Preview
@Composable
fun StorageHorizontalCardPreview(){
    StorageHorizontalCard(
        storage = storageLOCALTESTDATA,
        cardHeight = 60,
        isSelected = true,
        isSelectedMode = false,
        onEditStorageClick = {},
        onStorageClick = {},
        onStorageLongClick = {}
    )
}
@Composable
fun StorageHorizontalCard(
    storage: Storage,
    isSelected: Boolean,
    isSelectedMode: Boolean,
    cardHeight: Int,
    round: Int = 6,
    sizeCof: Float = 1f,
    onEditStorageClick: (storageId: Long) -> Unit,
    onStorageClick: () -> Unit,
    onStorageLongClick: ()-> Unit
) {
    val backgroundColorAnimate by animateColorAsState(
        targetValue = if (isSelected) MainDark else MainLight,
        animationSpec = tween(
            durationMillis = 100, easing = LinearOutSlowInEasing),
        label = "BgColorAnimation"
    )
    val backgroundColor  = if (isSelected) MainDark else MainLight

    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(round.dp))
            .background(backgroundColorAnimate)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick =  onStorageClick,
                onLongClick =onStorageLongClick
            )
            .height((cardHeight * sizeCof).dp)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(storage.color?.toComposeColor() ?: MainLight)
                .padding(8.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.min_size_method_draw_image),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "${storage.balance}",
                fontSize = (18 * sizeCof).sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SecondDark
            )
            Text(
                text = storage.name,
                fontSize = (14 * sizeCof).sp,
                color = SecondLight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (isSelected && !isSelectedMode) {
            IconButton(
                onClick = {
                    onEditStorageClick(storage.id)
                    Log.d("StorageHorizontalCard", "onClick")
                },
                modifier = Modifier.size((40 * sizeCof).dp)
            ) {
                Icon(
                    // Используем стандартную иконку карандаша из Material Icons
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Редактировать",
                    tint = SecondDark, // Или любой ваш цвет из темы
                    modifier = Modifier.size((20 * sizeCof).dp)
                )
            }
        }
    }
}