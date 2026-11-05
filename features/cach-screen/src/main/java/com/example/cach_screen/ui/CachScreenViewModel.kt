package com.example.cach_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cach_screen.domain.model.Purchase
import com.example.cach_screen.domain.repository.ICardHistoryRepository
import com.example.cach_screen.domain.repository.LoadType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CachScreenViewModel @Inject constructor(
    private val repository: ICardHistoryRepository
): ViewModel() {
    private val _list = MutableStateFlow<List<Purchase>>(emptyList())
    private val _syncStatus = MutableStateFlow<UiSate>(UiSate.Loading)

    val list = _list.asStateFlow()
    val syncStatus = _syncStatus.asStateFlow()

    var job: Job? =null

    fun refresh(){
        if (job?.isActive == true){
            job?.cancel()
        }
        job = viewModelScope.launch(Dispatchers.IO) {

            _syncStatus.value = UiSate.Loading
            repository.getHistory(20,0).collect { pair ->
                val (data, loadType) = pair
                when(loadType){
                    is LoadType.Syncing -> {
                        _syncStatus.update { UiSate.Syncing }
                    }
                    is LoadType.SyncSuccess -> {
                        _list.value = data
                        _syncStatus.update { UiSate.Success }
                    }
                    is LoadType.SyncError -> {
                        _syncStatus.update { UiSate.Error(loadType.message) }
                    }
                }
            }
            _syncStatus.value = UiSate.Idle
        }
    }

    fun upload(){
        if (job?.isActive == true){
            job?.cancel()
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            _syncStatus.value = UiSate.Loading
            repository.getHistory(20,_list.value.size).collect { pair ->
                val (data, loadType) = pair
                when(loadType){
                    is LoadType.Syncing -> {
                        _syncStatus.update { UiSate.Syncing }
                    }
                    is LoadType.SyncSuccess -> {
                        _list.value = data
                        _syncStatus.update { UiSate.Success }
                    }
                    is LoadType.SyncError -> {
                        _syncStatus.update { UiSate.Error(loadType.message) }
                    }
                }
            }
        }
    }
}

sealed class UiSate{
    object Syncing: UiSate()
    object Loading: UiSate()
    object Success: UiSate()
    object Idle: UiSate()
    data class Error(val message: String): UiSate()
}

