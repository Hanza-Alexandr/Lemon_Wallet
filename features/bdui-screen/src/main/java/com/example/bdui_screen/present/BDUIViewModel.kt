package com.example.bdui_screen.present

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bdui_screen.data.BDUIRepositoryImpl
import com.example.bdui_screen.domain.BDUIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

import com.example.bdui_screen.domain.IBDUIRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

@HiltViewModel
class BDUIViewModel @Inject constructor (
   private val bduiRepo: IBDUIRepository
): ViewModel() {
    val promoState: StateFlow<BDUIStateUI> = bduiRepo.observePromoScreen()
        .map { components -> BDUIStateUI.Loaded(components) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = BDUIStateUI.Loading
        )
}

sealed class BDUIStateUI(){
    object Loading: BDUIStateUI()
    data class Loaded(val components: List<BDUIComponent>): BDUIStateUI()
}