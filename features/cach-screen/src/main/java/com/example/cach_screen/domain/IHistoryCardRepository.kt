package com.example.cach_screen.domain

interface IHistoryCardRepository{
    suspend fun getHistory(limit: Int, offset: Int): HistoryCardState
}

