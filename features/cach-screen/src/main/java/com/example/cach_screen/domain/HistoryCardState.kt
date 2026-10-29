package com.example.cach_screen.domain

sealed class HistoryCardState{
    data class Success(val data: List<PurchaseHistoryCardItem>): HistoryCardState()
    data class Error(val message: String): HistoryCardState()
}