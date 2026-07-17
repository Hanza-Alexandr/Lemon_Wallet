package com.example.detailstorage_screen

import androidx.lifecycle.SavedStateHandle
import com.example.detailstorage_screen.ui.edit.EditStorageViewModel
import com.example.domain.reposytory.IColorRepository
import com.example.domain.reposytory.IStorageRepository
import com.example.domain.settings.ISettingsRepository
import com.example.navigation.INavigator
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class EditStorageViewModelTest {
    private val navigator = mockk<INavigator>()
    private val colorRepo = mockk<IColorRepository>()
    private val storageRepo = mockk<IStorageRepository>()
    private val settings = mockk<ISettingsRepository>()
    private val savedStateHandle = mockk<SavedStateHandle>()

    private lateinit var viewModel: EditStorageViewModel

    @Before
    fun setup(){
        viewModel = EditStorageViewModel(
            navigator = navigator,
            colorRepo = colorRepo,
            storageRepo = storageRepo,
            settings = settings,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun
}