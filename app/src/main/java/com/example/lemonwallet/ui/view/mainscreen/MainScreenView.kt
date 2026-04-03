package com.example.lemonwallet.ui.view.mainscreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lemonwallet.R
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.ui.view.mainscreen.storageblock.StorageBlock
import com.example.lemonwallet.viewmodel.MainViewModel

@Composable
fun MainScreenView(
    vm: MainViewModel
){
    var sizeMainButton by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val roundedCornerShapeBlock = 22.dp
    val storages by vm.storageList.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.lemonwallet.ui.theme.MainDark),
    ) {
        TopBar()
        Box{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = sizeMainButton)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StorageBlock(vm= vm, storages = storages, roundedCornerShapeBlock,{ newSelected, itemIndex ->
                    Log.i("My tag", "$storages")
                }, {})
                //LastOperations()
                //CashFlowGraffias()

            }
            MainButton(
                Modifier
                    .align(Alignment.BottomEnd)
            )
            { newSize ->
                sizeMainButton = with(density) { newSize.height.toDp() }
            }
        }


    }
}

@Composable
fun TopBar(){
    Row(
        modifier = Modifier
            .background(Color.White).fillMaxWidth().statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Lemon Wallet",
            modifier = Modifier
                .padding(start = 16.dp)
        )
        IconButton(
            modifier = Modifier,
            onClick = {}
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(R.drawable.group_4),
                contentDescription = null,
            )
        }

    }
}
























@Composable
fun CashFlowGraffias(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .heightIn(350.dp)
    ) {

    }
}

@Composable
fun LastOperations(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .heightIn(500.dp)
    ) {

    }
}

@Composable
fun MainButton(modifier: Modifier, onSizeGanged: (IntSize)-> Unit){
    Box(
        modifier = modifier
            .onSizeChanged(onSizeGanged)
            .padding(16.dp)

    ){
        androidx.compose.material3.Button(
            modifier = Modifier.size(60.dp),
            onClick = {}
        ) { }
    }
}