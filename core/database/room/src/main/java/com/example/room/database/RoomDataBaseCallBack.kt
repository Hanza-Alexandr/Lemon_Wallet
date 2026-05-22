package com.example.room.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.domain.settings.ISettingsRepository
import com.example.room.DefaultColor
import com.example.room.dao.ColorDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class RoomDataBaseCallBack @Inject constructor(
    // Используем Provider для ColorDao, чтобы не создавать базу раньше времени
    private val colorDaoProvider: Provider<ColorDao>,
    private val scope: CoroutineScope,
    private val settingsRepository: ISettingsRepository
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        scope.launch(Dispatchers.IO) {
            try {
                // Получаем userId (например, "GUEST") для инициализации
                val userId = settingsRepository.userIdFlow.first() ?: "GUEST"

                // Получаем DAO из провайдера
                val colorDao = colorDaoProvider.get()

                // Вставляем дефолтные цвета
                DefaultColor.getItems(userId).forEach { color ->
                    colorDao.insertColor(color)
                }
            } catch (e: Exception) {
                // Логируем ошибку, если что-то пошло не так при первичной вставке
                e.printStackTrace()
            }
        }
    }
}