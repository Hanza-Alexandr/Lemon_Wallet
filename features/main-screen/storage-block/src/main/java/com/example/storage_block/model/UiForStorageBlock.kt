package com.example.storage_block.model

import com.example.domain.domainmodel.DomainStorage

data class UiForStorageBlock(
    val balance: Long,
    val storage: DomainStorage,
    val isSelected: Boolean
)

