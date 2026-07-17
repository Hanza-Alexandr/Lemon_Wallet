package com.example.lemonwallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.settings.authorization.GetAuthStatusUseCase
import com.example.domain.settings.onboarding.OnBoardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onBoardingStatusUseCase: OnBoardingStatusUseCase,
    private val getAuthStatusUseCase: GetAuthStatusUseCase
): ViewModel() {
    val onBoardingStatus = onBoardingStatusUseCase.status.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
    val onAuthStatus = getAuthStatusUseCase.status.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

}
