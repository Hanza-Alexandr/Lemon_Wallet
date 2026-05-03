package com.example.storage_block.ui.components.blocks.components.storages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonwallet.ui.theme.Accent
import com.example.lemonwallet.ui.theme.MainLight
import com.example.lemonwallet.ui.theme.SecondDark

@Preview
@Composable
fun AccountHorizontalAddCardPreview(){
    AccountHorizontalAddCard(
        60,
        {}
    )
}

@Composable
fun AccountHorizontalAddCard(
    cardHeight: Int,
    onAddStorageClick: ()-> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .background(MainLight)
        .clickable {
            onAddStorageClick()
        }
    .height(cardHeight.dp)
    .padding(6.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(Accent)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = SecondDark,
            )
        }
        Column(
            modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Добавить счет",
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = SecondDark
            )
        }
    }
}

/**
@Composable
fun AccountHorizontalAddCard(
    cardHeight: Int,
    onAddStorageClick: ()-> Unit
) {
    Row(
        modifier = Modifier
            .height(cardHeight.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))

            .clickable {
                onAddStorageClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(com.example.lemonwallet.ui.theme.Accent),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ДОБАВИТЬ СЧЕТ",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = com.example.lemonwallet.ui.theme.SecondDark
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(com.example.lemonwallet.ui.theme.SecondDark),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = com.example.lemonwallet.ui.theme.Accent,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
        */