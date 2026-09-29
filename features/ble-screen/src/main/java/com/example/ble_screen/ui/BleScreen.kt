package com.example.ble_screen.ui

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.ble_screen.BleScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun BleScreen(modifier: Modifier = Modifier, vm: BleScreenViewModel = hiltViewModel()){


    val bleDeviceList: List<BleDevice> = emptyList()


    val context = LocalContext.current
    val bluetoothPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        // Проверяем, что ВСЕ запрашиваемые разрешения были одобрены пользователем
        val allGranted = permissionsMap.values.all { it }

        if (!allGranted) {
            vm.goBack()
            Toast.makeText(context, "Без разрешений сканирование невозможно", Toast.LENGTH_LONG).show()
        }
    }


    Column(modifier = modifier.fillMaxSize()) {
        LaunchedEffect(Unit) {
            permissionsLauncher.launch(bluetoothPermissions)
        }
        Row() { }
        LazyColumn() {
            items(bleDeviceList){ item ->
                BleListItem(item.name, item.signal)
            }
        }
    }
}

@Composable
private fun BleListItem(name: String?, signal: Int){
    Column() {
        Text(text = name?:"N/D")
        Text(text = signal.toString())
    }
}

data class BleDevice(
    val name: String?,
    val signal: Int
)