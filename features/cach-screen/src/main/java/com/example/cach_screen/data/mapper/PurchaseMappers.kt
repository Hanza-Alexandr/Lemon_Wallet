package com.example.cach_screen.data.mapper

import com.example.cach_screen.data.local.PurchaseEntity
import com.example.cach_screen.data.network.PurchaseDto
import com.example.cach_screen.domain.model.Purchase

fun PurchaseEntity.toPurchase(): Purchase {
    return Purchase(
        id = id,
        name = name,
        dataBuy = dataBuy
    )
}

fun PurchaseDto.toPurchase(): Purchase {
    return Purchase(
        id = id,
        name = name,
        dataBuy = dataBuy
    )
}