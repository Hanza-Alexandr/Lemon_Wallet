package com.example.main_screen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.GlobalTopBar
import com.example.ui.R

@Composable
fun TopBarMainScreen(
    titleClick: ()->Unit,
    onMore: () -> Unit
){
    GlobalTopBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lemon Wallet",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 16.dp).clickable(
                        onClick = titleClick
                    ),

            )
            IconButton(
                modifier = Modifier.padding(end = 6.dp),
                onClick = onMore
            ) {
                Icon(
                    painterResource(R.drawable.more_horiz),
                    contentDescription = "Delete",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }

}
@Preview
@Composable
fun TopBarMainScreenPreview(){
    TopBarMainScreen(titleClick = {},onMore =  {})
}