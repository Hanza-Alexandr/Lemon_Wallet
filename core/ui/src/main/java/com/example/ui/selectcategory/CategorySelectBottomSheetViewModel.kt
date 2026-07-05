package com.example.ui.selectcategory

import androidx.lifecycle.ViewModel
import com.example.domain.reposytory.ICategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

@HiltViewModel
class CategorySelectBottomSheetViewModel @Inject constructor(categoryRepo: ICategoryRepository): ViewModel() {

    private val _allCategories = MutableStateFlow(categoryRepo.getAllCategoriesFlow())
}