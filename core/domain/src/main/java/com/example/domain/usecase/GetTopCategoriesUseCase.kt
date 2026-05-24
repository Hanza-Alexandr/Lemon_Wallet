package com.example.domain.usecase

import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainColor
import com.example.domain.reposytory.ICategoryRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTopCategoriesUseCase @Inject constructor(
    private val categoryRepository: ICategoryRepository
) {
    suspend operator fun invoke(): List<DomainCategory>{
        return categoryRepository.getAllCategoriesFlow()
            .first() // Если репозиторий возвращает Flow, берем текущий список
            .take(5)
        //TODO нормальный топ а не первые 5
    }
}