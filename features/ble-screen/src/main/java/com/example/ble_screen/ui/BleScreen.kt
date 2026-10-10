package com.example.ble_screen.ui

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ble_screen.BleScreenState
import com.example.ble_screen.BleScreenViewModel
import com.example.ble_screen.model.BleDevice
import com.example.ble_screen.tools.calculateDistance
import com.example.ble_screen.useCase.BleState
import com.example.ui.RestartableStopwatch
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.collections.toList
import kotlin.math.pow

val bleRequiredPermissionsList: Array<String> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }



@Composable
fun BleScreen(modifier: Modifier = Modifier, vm: BleScreenViewModel = hiltViewModel()){

    val uiState: BleScreenState by vm.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

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
    uiState.currentDevice?.let { device ->
        BleDeviceDetailDialog(
            device = device,
            onDismiss = vm::onDismissDetailDialog,
            onConnect = {
                vm.stopScan()
                vm.startConnect(device.address)
            }
        )
    }

    if(uiState.connectState is BleState.Connected && uiState.currentDevice != null){
        BleConnectionStatusDialog(
            deviceName = uiState.currentDevice?.name,
            connectionState = uiState.connectState,
            readValues = uiState.readValues,
            onReadCharacteristic = { characteristic ->
                vm.readCharacteristic(characteristic)
            },
            onDismiss = vm::onDismissDetailDialog,
            onDisconnect = {
                vm.disconnectGatt()
                vm.onDismissDetailDialog()
            },
            onRetry = {
                vm.startConnect(uiState.currentDevice!!.address)
            }
        )
    }

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Cyan)
        .safeDrawingPadding()
    ) {
        LaunchedEffect(Unit) {
            permissionsLauncher.launch(bleRequiredPermissionsList)
        }
        PerformanceTestAnimation()
        Row() {
            RestartableStopwatch(resetKey = uiState.bleDeviceList.toList())
        }
        LazyColumn(modifier.fillMaxWidth().padding()) {
            items(uiState.bleDeviceList){ item ->
                BleListItem(item, onClick = {vm.onClickItemScanList(item)})
            }
        }
    }
}
