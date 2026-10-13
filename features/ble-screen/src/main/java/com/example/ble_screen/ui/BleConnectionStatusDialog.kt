package com.example.ble_screen.ui

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ble_screen.model.BleDevice
import com.example.ble_screen.useCase.BleState
import java.util.UUID

@Composable
fun BleConnectionStatusDialog(
    device: BleDevice,
    connectionState: BleState,
    isReading: Pair<Boolean, UUID?>,
    readValues: Map<UUID, ByteArray> = emptyMap(),
    onReadCharacteristic: (BluetoothGattCharacteristic) -> Unit = {},
    onDismiss: () -> Unit,
    onDisconnect: () -> Unit,
    onRetry: () -> Unit = {}
) {
    val title = when (connectionState) {
        is BleState.Connected -> "Подключение к ${device.name ?: "BLE устройству"} "
        is BleState.Connecting -> "Подключение ${device.name ?: "BLE устройству"}"
        is BleState.DiscoveringServices -> "Поиск сервисов"
        is BleState.Error -> "Ошибка подключения"
        is BleState.Disconnected -> ""
    }
    AlertDialog(
        onDismissRequest = {
            if (connectionState is BleState.Connecting || connectionState is BleState.DiscoveringServices) {
                onDisconnect()
            }
            onDismiss()
        },
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (connectionState) {
                    is BleState.Connecting -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Подключение...",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Установление соединения с устройством",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    is BleState.DiscoveringServices -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Поиск сервисов...",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Считывание GATT-характеристик",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    is BleState.Connected -> {
                        var expandedServiceUuid by remember { mutableStateOf<UUID?>(null) }
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Успешно",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Успешно подключено!",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Найдено GATT-сервисов: ${connectionState.gatt.services.size}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Список сервисов с информацией и чтением характеристик
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 280.dp)
                        ) {
                            items(
                                items = connectionState.gatt.services,
                                key = { service -> service.uuid.toString() }
                            ) { service ->
                                GattServiceItem(
                                    service = service,
                                    isExpanded = expandedServiceUuid == service.uuid,
                                    readValues = readValues,
                                    isReading = isReading,
                                    onReadCharacteristic = onReadCharacteristic,
                                    onClick = {
                                        expandedServiceUuid = if (expandedServiceUuid == service.uuid) null else service.uuid
                                    }
                                )
                            }
                        }
                    }

                    is BleState.Error -> {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Ошибка",
                            tint = Color(0xFFC62828),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Ошибка подключения",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFFC62828)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Код ошибки GATT: ${connectionState.status}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    is BleState.Disconnected -> {
                        Text(
                            text = "Устройство отключено",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        },
        confirmButton = {
            when (connectionState) {
                is BleState.Connected -> {
                    Button(onClick = onDismiss) {
                        Text("Готово")
                    }
                }
                is BleState.Error -> {
                    Button(onClick = onRetry) {
                        Text("Повторить")
                    }
                }
                else -> {}
            }
        },
        dismissButton = {
            when (connectionState) {
                is BleState.Connecting, is BleState.DiscoveringServices -> {
                    OutlinedButton(onClick = {
                        onDisconnect()
                        onDismiss()
                    }) {
                        Text("Отмена")
                    }
                }
                is BleState.Connected -> {
                    TextButton(onClick = {
                        onDisconnect()
                        onDismiss()
                    }) {
                        Text("Отключиться", color = Color(0xFFC62828))
                    }
                }
                else -> {
                    TextButton(onClick = onDismiss) {
                        Text("Закрыть")
                    }
                }
            }
        }
    )
}

@Composable
private fun GattServiceItem(
    service: BluetoothGattService,
    isExpanded: Boolean,
    isReading: Pair<Boolean, UUID?>,
    readValues: Map<UUID, ByteArray>,
    onReadCharacteristic: (BluetoothGattCharacteristic) -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
            else Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Сервис",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = service.uuid.toString(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Характеристики (${service.characteristics.size}):",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                service.characteristics.forEach { characteristic ->
                    GattCharacteristicItem(
                        characteristic = characteristic,
                        readValues = readValues,
                        isReading = isReading,
                        onReadCharacteristic = onReadCharacteristic
                    )
                }
            }
        }
    }
}

@Composable
private fun GattCharacteristicItem(
    characteristic: BluetoothGattCharacteristic,
    isReading: Pair<Boolean, UUID?>,
    readValues: Map<UUID, ByteArray>,
    onReadCharacteristic: (BluetoothGattCharacteristic) -> Unit
) {
    val isReadable = (characteristic.properties and BluetoothGattCharacteristic.PROPERTY_READ) != 0
    val value = readValues[characteristic.uuid]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable(enabled = isReadable) {
                onReadCharacteristic(characteristic)
            }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Характеристика:",
                    fontSize = 9.sp,
                    color = Color.Gray
                )
                Text(
                    text = characteristic.uuid.toString(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace,
                    color = Color(0xFF1C1B1F)
                )
            }

            if (isReadable && value == null) {
                if (isReading.first){
                    if (isReading.second == characteristic.uuid){
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    else{
                        Icon(
                            imageVector = Icons.Default.Block,
                            contentDescription = "Приостановка",
                            tint = Color(0xFFC62828),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else{
                    Text(
                        text = "Считать",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Свойства: ${getCharacteristicPropertiesText(characteristic.properties)}",
            fontSize = 9.sp,
            color = Color(0xFF6200EE),
            fontWeight = FontWeight.Medium
        )

        // Отображение прочитанного значения (если оно есть)
        if (value != null) {
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
            Spacer(modifier = Modifier.height(4.dp))

            val hexString = value.joinToString(" ") { "%02X".format(it) }
            val textString = try {
                String(value, Charsets.UTF_8).filter { it.isLetterOrDigit() || it.isWhitespace() }
            } catch (e: Exception) {
                ""
            }

            Text(
                text = "HEX: 0x$hexString",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color(0xFF2E7D32)
            )

            if (textString.isNotBlank()) {
                Text(
                    text = "Текст: \"$textString\"",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1565C0)
                )
            }
        }
    }
}

private fun getCharacteristicPropertiesText(properties: Int): String {
    val props = mutableListOf<String>()
    if (properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) props.add("READ")
    if (properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) props.add("WRITE")
    if (properties and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE != 0) props.add("WRITE_NO_RESP")
    if (properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) props.add("NOTIFY")
    if (properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) props.add("INDICATE")
    return if (props.isEmpty()) "NONE" else props.joinToString(" | ")
}

