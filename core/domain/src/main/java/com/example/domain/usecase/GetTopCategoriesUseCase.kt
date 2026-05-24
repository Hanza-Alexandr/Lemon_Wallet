package com.example.domain.usecase

import com.example.domain.domainmodel.DomainCategory
import com.example.domain.domainmodel.DomainColor
import com.example.domain.reposytory.ICategoryRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetTopCategoriesUseCase @Inject constructor(
    private val categoryRepository: ICategoryRepository
) {
    suspend operator fun invoke(): List<DomainCategory>{
        return emptyList() //TODO
    }
}