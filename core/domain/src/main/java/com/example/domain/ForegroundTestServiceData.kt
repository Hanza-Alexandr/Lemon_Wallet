package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForegroundTestServiceData @Inject constructor() {

    private val _data = MutableSharedFlow<String>()
    val data = _data as Flow<String>

    suspend fun emit(value: String){
        _data.emit(value)
    }
}