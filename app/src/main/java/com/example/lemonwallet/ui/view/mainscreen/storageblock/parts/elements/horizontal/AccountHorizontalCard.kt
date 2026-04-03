package com.example.lemonwallet.ui.view.mainscreen.storageblock.parts.elements.horizontal

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonwallet.R
import com.example.lemonwallet.model.domain.Storage

@Composable
fun AccountHorizontalCard(
    isSelected: Boolean,
    storage: Storage,
    balance: Double,
    elementHeight: Int = 60,
    round: Int = 6,
    sizeCof: Float = 1f,
    onClick: () -> Unit
) {
    val backgroundColor  = if (isSelected) com.example.lemonwallet.ui.theme.MainDark else com.example.lemonwallet.ui.theme.MainLight
    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(round.dp))
            //.border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(round.dp))
            .background(backgroundColor)
            .clickable {
                //Log.i("My Tag", "${storage.isSelect}")
                //val newSelected = !storage.isSelect
                onClick()
            }
            .height((elementHeight * sizeCof).dp)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(storage.color.toDomain())
                .padding(8.dp)){
            Icon(
                painter = painterResource(R.drawable.min_size_method_draw_image),
                contentDescription = null
            )
        }
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "${balance}₽",
                fontSize = (18 * sizeCof).sp,
                color = com.example.lemonwallet.ui.theme.SecondDark
            )
            Text(
                text = storage.name,
                fontSize = (14 * sizeCof).sp,
                color = com.example.lemonwallet.ui.theme.SecondLight,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}