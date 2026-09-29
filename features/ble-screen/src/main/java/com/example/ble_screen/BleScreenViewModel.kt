package com.example.ble_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ble_screen.ui.BleDevice
import com.example.navigation.INavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BleScreenViewModel @Inject constructor (
    private val navigator: INavigator,
    ): ViewModel() {

    private val _bleDeviceList = MutableStateFlow(emptyList<BleDevice>())
    val bleDeviceList = _bleDeviceList.asStateFlow()

    init {

    }


    private fun startScan(){
        viewModelScope.launch(Dispatchers.IO){

        }
    }

    fun goBack(){
        navigator.goBack()
    }

}

