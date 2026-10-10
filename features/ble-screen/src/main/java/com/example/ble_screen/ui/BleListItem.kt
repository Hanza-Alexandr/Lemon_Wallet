package com.example.ble_screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.SignalCellularAlt1Bar
import androidx.compose.material.icons.filled.SignalCellularAlt2Bar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale
import com.example.ble_screen.model.BleDevice

@Composable
fun BleListItem(
    device: BleDevice,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    // Определяем цвет и иконку сигнала на основе значения RSSI
    val (signalColor, signalIcon) = when {
        device.rssi >= -65 -> Color(0xFF2E7D32) to Icons.Default.SignalCellularAlt
        device.rssi >= -80 -> Color(0xFFEF6C00) to Icons.Default.SignalCellularAlt2Bar
        else -> Color(0xFFD32F2F) to Icons.Default.SignalCellularAlt1Bar
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка Bluetooth в цветном кружке
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bluetooth,
                    contentDescription = "Bluetooth Device",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Основная информация об устройстве
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = device.name ?: "Неизвестное устройство",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (device.name != null) Color(0xFF1C1B1F) else Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = device.address,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Чипы с дистанцией и уровнем сигнала RSSI
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Чип расчётного расстояния
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF1F3F4)
                    ) {
                        Text(
                            text = String.format(LocalLocale.current.platformLocale, "≈ %.1f м", device.distance),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3C4043),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Чип RSSI с динамическим цветом
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = signalColor.copy(alpha = 0.12f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = signalIcon,
                                contentDescription = null,
                                tint = signalColor,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${device.rssi.toInt()} dBm",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = signalColor
                            )
                        }
                    }
                }
            }
        }
    }
}