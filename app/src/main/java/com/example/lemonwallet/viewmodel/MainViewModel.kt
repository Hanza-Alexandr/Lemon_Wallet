package com.example.lemonwallet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lemonwallet.model.StateDomain
import com.example.lemonwallet.model.repository.LocalDataStoreRepository
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.service.StorageService
import com.example.lemonwallet.ui.state.StorageBlockUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class AuthState{
    object Loading: AuthState()
    data class Auth(val id: Int): AuthState()
    object Guest: AuthState()
    object NoAuth: AuthState()
}
class MainViewModel(private val dataStoreRepo: LocalDataStoreRepository,private val storageService: StorageService): ViewModel() {


    val storageList: StateFlow<List<Storage>?> = storageService.getFlowStorageList()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Экономит ресурсы при сворачивании
            initialValue = null
        )

    private val _storageBlockState = MutableStateFlow(
        StorageBlockUiState(
            selectedStorages = setOf(0,1)
        )
    )
    val storageBlockState = _storageBlockState.asStateFlow()

    fun switchSelect(index: Int) {
        _storageBlockState.update { state ->
            val newSelected = state.selectedStorages.toMutableSet().apply {
                if (contains(index)) {
                    if(size >1) {
                        remove(index)
                    }
                } else {
                    add(index)
                }
            }
            state.copy(selectedStorages = newSelected)
        }
    }


    val stateAuth = dataStoreRepo.userIdFlow
        .map {
            when (it) {
                null -> AuthState.NoAuth
                -1 -> AuthState.Guest
                else -> AuthState.Auth(it)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState.Loading
        )

    val isFirstOpeningApp = dataStoreRepo.isFirstOpeningApp
        .stateIn(scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun markFirstAppOpeningCompleted(){
        viewModelScope.launch {
            dataStoreRepo.markFirstAppOpeningCompleted()
        }
    }

    fun logIn(id: Int){
        viewModelScope.launch {
            dataStoreRepo.logIn(id)
        }
    }

    fun logOut(){
        viewModelScope.launch {
            dataStoreRepo.logOut()
        }
    }

    fun getStorageBalance(storage: Storage): Double{
        return when(val a = storageService.getStorageBalance(storage)){
            is StateDomain.Success -> a.domain
            is StateDomain.Error -> 0.0
        }
    }

}