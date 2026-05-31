package com.example.categoryselection_screen.ui

import UiCategory
import androidx.lifecycle.ViewModel
import com.example.domain.domainmodel.DomainCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor(): ViewModel() {
    val allCategories = MutableStateFlow<List<UiCategory>?>(null)

}