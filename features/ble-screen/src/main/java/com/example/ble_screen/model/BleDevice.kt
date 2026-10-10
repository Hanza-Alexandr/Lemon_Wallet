package com.example.ble_screen.model

import com.example.ble_screen.tools.calculateDistance

data class BleDevice(
    val name: String?,
    val rssi: Double,
    val address: String,
    val txPower: Double,
    val distance: Double = calculateDistance(rssi, txPower),
    val lastSeenTimestamp: Long
)