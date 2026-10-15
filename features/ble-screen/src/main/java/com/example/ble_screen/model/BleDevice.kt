package com.example.ble_screen.model

import com.example.ble_screen.tools.calculateDistance

data class BleDevice(
    val address: String,
    val name: String?,
    val rssi: Double,
    val txPower: Double,
    val lastSeenTimestamp: Long
) {
    val distance: Double = calculateDistance(rssi, txPower)

    // Сравниваем устройства по MAC-адресу И по имени
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BleDevice) return false
        return address == other.address && name == other.name
    }

    override fun hashCode(): Int {
        var result = address.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}