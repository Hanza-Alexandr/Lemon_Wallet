package com.example.storage_block.model

import com.example.domain.settings.ISettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.collections.remove
import kotlin.compareTo
import kotlin.text.clear

class OnSelectStorageInStorageBlockUseCase @Inject constructor(
    val settings: ISettingsRepository,
) {
    suspend operator fun invoke(isLongClick: Boolean,idStorage: String){
        val currentSelected = settings.idSelectedStorageFlow.first()
        val isAlreadySelected = currentSelected.contains(idStorage)
        val isSelectModeActive = currentSelected.size > 1

        val newSelected = currentSelected.toMutableSet().apply {
            when {
                isLongClick -> {
                    if (isSelectModeActive) clear()
                    add(idStorage)
                }
                isAlreadySelected -> {
                    if (size > 1) remove(idStorage)
                }
                else -> {
                    if (size == 1) clear()
                    add(idStorage)
                }
            }
        }
        settings.saveSelectedIds(newSelected)
    }
}