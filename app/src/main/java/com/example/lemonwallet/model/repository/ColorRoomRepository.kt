package com.example.lemonwallet.model.repository


import com.example.lemonwallet.model.domain.DomainColor
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.NewColor
import com.example.lemonwallet.model.domain.UserColor
import com.example.lemonwallet.model.roomdb.dao.ColorDao
import com.example.lemonwallet.model.service.AccountService
import com.example.lemonwallet.model.state.AuthorizationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ColorRoomRepository @Inject constructor(private val colorDao: ColorDao, private val accountService: AccountService) : IColorRepository {


    override fun getAllFlow(): Flow<List<ExistColor>> {
        return colorDao.getAllColorsFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Long): ExistColor? {
        return colorDao.getColorById(id)?.toDomain()
    }

    override suspend fun update(color: UserColor): UserColor?{
        val userId = accountService.stateAuth.first().let {
            when(it){
                is AuthorizationState.Authorization -> it.id.toLong()
                else -> -1L
            }
        }
        val entity = color.toRoomEntityColor()
        colorDao.insertColor(entity)
        try {
            return getById(color.id) as UserColor?
        }
        catch (e: IllegalArgumentException)
        {
            //На случай если из бд вдруг придет цвет с некорректным userId
            throw IllegalArgumentException("❌Ошибка. Пришел некорректны ответ от БД: ${e.message}")
        }
    }

    override suspend fun save(color: NewColor): ExistColor?{
        val userId = accountService.stateAuth.first().let {
            when(it){
                is AuthorizationState.Authorization -> it.id.toLong()
                else -> -1L
            }
        }
        val entity = color.toRoomEntityColor(userId)
        val newId = colorDao.insertColor(entity)
        try {
            return getById(newId) as UserColor?
        }
        catch (e: IllegalArgumentException){
            //На случай если из бд вдруг придет цвет с некорректным userId
            throw IllegalArgumentException("❌Ошибка. Пришел некорректны ответ от БД: ${e.message}")
        }
    }

    override suspend fun delete(color: UserColor): UserColor? {
        TODO("НЕ СДЕЛАЛ")
    }
}
