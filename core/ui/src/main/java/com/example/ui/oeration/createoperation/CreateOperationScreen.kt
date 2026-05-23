package com.example.ui.oeration.createoperation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.ui.oeration.GlobalDetailsOperationContent
import com.example.ui.storage.GlobalDetailStorageContent
import com.example.ui.storage.createstorage.TopBarCreateStorage
import com.example.ui.them.MainDark

@Composable
fun CreateOperationScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDark)
    ) {
        TopBarCreateOperation (
            onBack = {

            },
            onSave = {

            }
        )

        GlobalDetailsOperationContent()

    }
}