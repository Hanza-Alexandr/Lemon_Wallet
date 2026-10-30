package com.example.cach_screen.data.network

import com.example.cach_screen.domain.NetworkResult

interface INetWorkCardHistoryDataSource{
    suspend fun getHistory(limit: Int, offset: Int): NetworkResult<List<PurchaseDto>>
}