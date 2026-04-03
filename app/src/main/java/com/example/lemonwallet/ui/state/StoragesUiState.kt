package com.example.lemonwallet.ui.state

import com.example.lemonwallet.model.domain.Storage

data class StoragesUiState(
    val storages: List<Storage> = emptyList(),
    val selectedIds: Set<Long> = emptySet(),
    //val isLoading: Boolean = false
){
    fun isSelected(id: Long): Boolean{
        return selectedIds.contains(id)
    }
}