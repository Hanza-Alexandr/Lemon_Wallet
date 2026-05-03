package com.example.domain.state

/**
 * Закрытый, защищенный класс состояния готовности данных.
 */
sealed class DataLoadingState<out T>{
    object Loading: DataLoadingState<Nothing>()
    data class Success<T>(val result: T): DataLoadingState<T>()
}