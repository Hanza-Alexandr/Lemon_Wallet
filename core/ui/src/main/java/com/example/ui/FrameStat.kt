package com.example.ui

import android.util.Log
import android.view.FrameMetrics
import android.view.Window

class ScreenSmoothnessTracker {

    private var totalFrames = 0
    private var jankyFrames = 0
    private val allFrameDurations = mutableListOf<Long>()

    // Слушатель метрик
    private val metricsListener = Window.OnFrameMetricsAvailableListener { _, frameMetrics, _ ->
        val metricsCopy = FrameMetrics(frameMetrics)
        val totalDurationMs = metricsCopy.getMetric(FrameMetrics.TOTAL_DURATION) / 1_000_000

        totalFrames++
        allFrameDurations.add(totalDurationMs)

        // Порог лага: 16мс для 60Гц, но лучше завязаться на аппаратную частоту экрана.
        // Для надежности берем > 16мс как универсальный маркер jank-кадра.
        if (totalDurationMs > 16) {
            jankyFrames++
        }
    }

    // Включаем сбор при старте анимации/экрана
    fun startTracking(window: Window) {
        totalFrames = 0
        jankyFrames = 0
        allFrameDurations.clear()
        window.addOnFrameMetricsAvailableListener(metricsListener, android.os.Handler(android.os.Looper.getMainLooper()))
    }

    // Выключаем сбор и выводим ИТОГОВУЮ МЕТРИКУ
    fun stopTrackingAndLog(window: Window, screenName: String) {
        try {
            window.removeOnFrameMetricsAvailableListener(metricsListener)
        } catch (_: Exception) {}

        if (totalFrames == 0) return

        // 1. Считаем процент лагов
        val jankPercentage = (jankyFrames.toFloat() / totalFrames.toFloat()) * 100

        // 2. Считаем 95-й перцентиль (какое время не превышали 95% кадров)
        allFrameDurations.sort()
        val index95 = (allFrameDurations.size * 0.95).toInt().coerceAtMost(allFrameDurations.size - 1)
        val percentile95 = allFrameDurations.getOrNull(index95) ?: 0L

        // Среднее время кадра
        val averageFrameTime = allFrameDurations.average()

        // ВЫВОДИМ ЕДИНУЮ МЕТРИКУ В ЛОГИ
        Log.i("SmoothnessQA", """
            === ОТЧЕТ ПО ПЛАВНОСТИ ДЛЯ: $screenName ===
            Всего кадров отрисовано: $totalFrames
            Процент лагов (Jank Rate): ${String.format("%.2f", jankPercentage)}%  <-- ГЛАВНАЯ МЕТРИКА
            95-й перцентиль скорости: $percentile95 мс             <-- Показывает пиковые лаги
            Среднее время кадра: ${String.format("%.2f", averageFrameTime)} мс
            ==================================================
        """.trimIndent())
    }
}