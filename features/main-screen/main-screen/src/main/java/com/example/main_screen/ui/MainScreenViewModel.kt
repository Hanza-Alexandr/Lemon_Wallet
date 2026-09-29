package com.example.main_screen.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.IServiceController
import com.example.domain.usecase.GetSelectStorageUseCase
import com.example.main_screen.model.ForegroundTestServiceController
import com.example.domain.ForegroundTestServiceData
import com.example.navigation.INavigator
import com.example.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val navigator: INavigator,
    private val getSelectStorageUseCase: GetSelectStorageUseCase,
    private val serviceController: IServiceController,
    private val foregroundTestServiceRepo: ForegroundTestServiceData
): ViewModel() {

    val serviceData = foregroundTestServiceRepo.data.stateIn(
        viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )



    fun onMainBottomClick(){
        viewModelScope.launch {
            val a = getSelectStorageUseCase.invoke().first()
            if (a.size ==1){
                navigator.navigateTo(NavigationRoute.CreateOperation(a[0].id))
            }


        }
    }

    fun onTitleClick(){
        serviceController.startService()
    }

    fun onMore(){
        navigator.navigateTo(NavigationRoute.CameraPreviewScreen)
    }
}