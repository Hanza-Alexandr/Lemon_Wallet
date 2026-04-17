package com.example.lemonwallet.ui.view.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonwallet.R

@Preview
@Composable
fun EditStorageView(
    storageId: Long = 1L,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                modifier = Modifier,
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                )
            }
            Text(
                text = "Edit Storage",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1F)

            )
            IconButton(
                modifier = Modifier,
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.delete),
                    contentDescription = null,
                )
            }
            IconButton(
                modifier = Modifier,
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.done),
                    contentDescription = null,
                )
            }

        }

    }

}