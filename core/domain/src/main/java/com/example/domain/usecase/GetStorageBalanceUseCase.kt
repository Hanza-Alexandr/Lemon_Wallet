package com.example.domain.usecase

import com.example.domain.domainmodel.DomainStorage
import javax.inject.Inject
import kotlin.random.Random

class GetStorageBalanceUseCase @Inject constructor(){
    suspend operator fun invoke(storage: DomainStorage): Long {
        return Random.nextLong(100, 1000000) //TODO
    }
}