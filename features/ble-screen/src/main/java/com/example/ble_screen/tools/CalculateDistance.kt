package com.example.ble_screen.tools

import kotlin.math.pow

fun calculateDistance(rssi: Double, txPower: Double): Double {
    if (rssi == 0.0) return -1.0

    // Если txPower не определен ОС (равен 127 или 0),
    // используем эталонную мощность сигнала на 1 метре (-59 dBm)
    val txPower1m = if (txPower in -100.0..-30.0) txPower else -59.0

    val ratio = rssi * 1.0 / txPower1m
    return if (ratio < 1.0) {
        ratio.pow(10.0)
    } else {
        (0.89976) * ratio.pow(7.7095) + 0.111
    }
}
