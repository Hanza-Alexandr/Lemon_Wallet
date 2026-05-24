package com.example.room.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.domain.usecase.GetUserIdUseCase
import com.example.room.DefaultData
import com.example.room.dao.CategoryDao
import com.example.room.dao.ColorDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class RoomDataBaseCallBack @Inject constructor(
    // Используем Provider для ColorDao, чтобы не создавать базу раньше времени
    private val colorDaoProvider: Provider<ColorDao>,
    private val categoryDaoProvider: Provider<CategoryDao>,

    private val scope: CoroutineScope,
    private val getUserIdUseCase: GetUserIdUseCase
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        scope.launch(Dispatchers.IO) {
            try {
                // Получаем userId (например, "GUEST") для инициализации
                val userId = getUserIdUseCase.getId()
                // Получаем DAO из провайдера
                val colorDao = colorDaoProvider.get()
                val categoryDao = categoryDaoProvider.get()

                // Вставляем дефолтные цвета
                DefaultData.getColorItems(userId).forEach { color ->
                    colorDao.insertColor(color)
                }
                DefaultData.getCategoryItems(userId).forEach { category ->
                    categoryDao.insertCategory(category)
                }
            } catch (e: Exception) {
                // Логируем ошибку, если что-то пошло не так при первичной вставке
                e.printStackTrace()
            }
        }
    }
}