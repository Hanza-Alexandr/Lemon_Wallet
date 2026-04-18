package com.example.lemonwallet.ui.view.topbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonwallet.R

@Composable
fun TopBarEditStorage(
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSave: () -> Unit
){
    GlobalTopBar {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(painterResource(R.drawable.close), contentDescription = "Close")
            }
            Text(
                text = "Редактирование",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
            IconButton(onClick = onDelete) {
                Icon(painterResource(R.drawable.delete), contentDescription = "Delete")
            }
            IconButton(onClick = onSave) {
                Icon(painterResource(R.drawable.done), contentDescription = "Save")
            }
        }
    }
}

@Preview
@Composable
fun TopBarEditingStoragePreview(){
    TopBarEditStorage(
        onBack = {},
        onDelete = {},
        onSave = {}
    )
}