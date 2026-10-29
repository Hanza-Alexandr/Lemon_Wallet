package com.example.cach_screen.domain

data class PurchaseHistoryCardItem(
    val id: String,
    val dataBuy: String,
    val purchasesId: List<Purchase>

)

data class Purchase(
    val id: String,
    val name: String
)