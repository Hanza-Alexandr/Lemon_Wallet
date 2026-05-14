package com.example.lemonwallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.settings.authorization.GetAuthStatusUseCase
import com.example.domain.settings.onboarding.OnBoardingStatusInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onBoardingStatusInteractor: OnBoardingStatusInteractor,
    private val getAuthStatusUseCase: GetAuthStatusUseCase
): ViewModel() {
    private val _onBoardingStatus = MutableStateFlow<Boolean?>(null)
    val onBoardingStatus = _onBoardingStatus.asStateFlow()

    private val _onAuthStatus = MutableStateFlow<Boolean?>(null)
    val onAuthStatus = _onAuthStatus.asStateFlow()

    init {
        viewModelScope.launch {
            _onBoardingStatus.value = this@MainViewModel.onBoardingStatusInteractor.getStatus()
            _onAuthStatus.value = getAuthStatusUseCase.invoke()
        }
    }
}