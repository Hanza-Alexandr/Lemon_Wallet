package com.example.cach_screen.domain

sealed class NetworkResult<T>{
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error<T>(val message: String): NetworkResult<T>()
}



