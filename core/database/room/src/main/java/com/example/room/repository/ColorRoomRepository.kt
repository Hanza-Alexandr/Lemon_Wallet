package com.example.room.repository

import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.NewDomainColor
import com.example.domain.reposytory.IColorRepository
import com.example.domain.state.AuthorizationState
import com.example.domain.usecase.AccountService
import com.example.domain.usecase.GetUserIdUseCase
import com.example.room.dao.ColorDao
import com.example.room.entity.toDomain
import com.example.room.entity.toRoomEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ColorRoomRepository @Inject constructor(
    private val colorDao: ColorDao,
    private val getUserId: GetUserIdUseCase
) :IColorRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllColorsFlow(): Flow<List<DomainColor>> {
        return getUserId.getIfFLow().flatMapLatest { userId ->
            colorDao.getAllColorsFlow(userId).map { list ->
                list.map {
                    it.toDomain()
                }
            }
        }
    }


    override suspend fun getColorById(colorId: String): DomainColor? {
        return colorDao.getColorById(colorId)?.toDomain()
    }

    override suspend fun saveColor(color: NewDomainColor) {
        colorDao.insertColor(color.toRoomEntity())
    }

    override suspend fun updateColor(color: DomainColor) {
        colorDao.updateColorHex(
            colorId = color.id,
            newHex = color.hex
        )
    }

    override suspend fun deleteColor(colorId: String): Boolean {
        try{
            colorDao.softDeleteColor(colorId)
            return true
        }catch (e: Exception){
            return false
        }
    }

    override suspend fun migrateGuestColors(newUserId: String) {
        TODO("Not yet implemented")
    }
}