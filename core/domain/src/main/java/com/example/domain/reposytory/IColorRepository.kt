package com.example.domain.reposytory

import com.example.domain.domainmodel.DomainColor
import com.example.domain.domainmodel.NewDomainColor
import kotlinx.coroutines.flow.Flow

interface IColorRepository {
    suspend fun getAllColorsFlow(): Flow<List<DomainColor>>

    /**
     * Получить конкретный цвет по его уникальному идентификатору (UUID).
     */
    suspend fun getColorById(colorId: String): DomainColor?

    /**
     * Сохранить новый цвет.
     * На вход принимаем данные для создания, на выходе получаем полную модель с ID.
     */
    suspend fun saveColor(color: NewDomainColor)

    /**
     * Обновить существующий цвет (например, изменить HEX).
     */
    suspend fun updateColor(color: DomainColor)

    /**
     * Удалить цвет.
     * В реализации это будет soft-delete (пометка is_deleted = 1),
     * чтобы не нарушить целостность данных в операциях, которые уже используют этот цвет.
     */
    suspend fun deleteColor(colorId: String): Boolean

    /**
     * Метод для миграции данных гостя к зарегистрированному пользователю.
     */
    suspend fun migrateGuestColors(newUserId: String)
}