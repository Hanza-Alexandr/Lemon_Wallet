package com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.lastoperations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.GeneralTransaction
import com.example.domain.Operation
import com.example.domain.TransferTransaction

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun LastOperationListItemPreview() {
}


@Composable
fun LastOperationListItem(operation: Operation) {


}

@Composable
fun OperationItem(operation: Operation){

}

@Composable
fun TransferOperation(transfer: TransferTransaction){

}
@Composable
fun GeneralTransaction(generalTransaction: GeneralTransaction){

}
