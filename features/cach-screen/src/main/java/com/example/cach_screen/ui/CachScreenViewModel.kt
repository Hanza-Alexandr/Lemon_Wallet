package com.example.cach_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cach_screen.domain.HistoryCardState
import com.example.cach_screen.domain.IHistoryCardRepository
import com.example.cach_screen.domain.PurchaseHistoryCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CachScreenViewModel @Inject constructor(
    private val repository: IHistoryCardRepository
): ViewModel() {
    private val _list = MutableStateFlow<List<PurchaseHistoryCardItem>>(emptyList())
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
            when(val res = repository.getHistory(10,0)){
                is HistoryCardState.Success-> {
                    _list.value = res.data
                    _syncStatus.value = UiSate.Success
                }
                is HistoryCardState.Error -> {
                    _syncStatus.value = UiSate.Error(res.message)
                }
            }
        }
    }

    fun upload(){
        if (job?.isActive == true){
            job?.cancel()
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            _syncStatus.value = UiSate.Loading
            when(val res = repository.getHistory(10,_list.value.size)){
                is HistoryCardState.Success-> {
                    _list.value += res.data
                    _syncStatus.value = UiSate.Success
                }
                is HistoryCardState.Error -> {
                    _syncStatus.value = UiSate.Error(res.message)
                }
            }
        }
    }
}

sealed class UiSate{
    object Loading: UiSate()
    object Success: UiSate()
    data class Error(val message: String): UiSate()
}

