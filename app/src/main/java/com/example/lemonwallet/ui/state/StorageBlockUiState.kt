package com.example.lemonwallet.ui.state

import com.example.lemonwallet.model.domain.Storage

data class StorageBlockUiState(
    //val storages: List<Storage> = emptyList(),
    val selectedStorages: Set<Int>,
    //val isLoading: Boolean = false
){
    val isSelectedMode: Boolean = selectedStorages.size > 1
    fun isSelected(id: Int): Boolean{
        return selectedStorages.contains(id)
    }

}