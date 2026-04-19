package com.example.lemonwallet.model.repository

import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.NewColor
import com.example.lemonwallet.model.domain.UserColor
import com.example.lemonwallet.model.roomdb.dao.ColorDao
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ColorRoomRepository @Inject constructor(private val colorDao: ColorDao) : IColorRepository {
    override fun getAllFlow(): Flow<List<ExistColor>> {
        return colorDao.getAllColorsFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Long): ExistColor? {
        return colorDao.getColorById(id)?.toDomain()
    }

    override suspend fun save(color: UserColor): ExistColor? {
        val entity = ColorRoomEntity(
            id = color.id,
            userId = color.owner.userId,
            hexCode = color.hexCode
        )
        colorDao.insertColor(entity)
        return getById(color.id)
    }

    override suspend fun save(color: NewColor): ExistColor? {
        val entity = ColorRoomEntity(
            id = 0, // Auto-generate
            userId = color.owner.userId,
            hexCode = color.hexCode
        )
        val newId = colorDao.insertColor(entity)
        return getById(newId)
    }

    override suspend fun delete(color: UserColor): Boolean {
        // Implementation for delete if needed
        return false
    }
}
