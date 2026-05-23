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
                if (userId.contains("GUEST")) {
                    val colorDao = colorDaoProvider.get()
                    val categoryDao = categoryDaoProvider.get()

                    // 3. Загружаем предустановленные данные
                    val defaultColors = DefaultData.getColorItems(userId)
                    val defaultCategories = DefaultData.getCategoryItems(userId)
                    defaultColors.forEach {
                        colorDao.insertColor(it)
                    }
                    defaultCategories.forEach {
                        categoryDao.insertCategory(it)
                    }
                    // Здесь же можно добавить дефолтный кошелек или категории, если нужно:
                    // storageDaoProvider.get().insert(DefaultStorage.get(userId))
                }

                // Получаем DAO из провайдера
                val colorDao = colorDaoProvider.get()

                // Вставляем дефолтные цвета
                DefaultData.getColorItems(userId).forEach { color ->
                    colorDao.insertColor(color)
                }
            } catch (e: Exception) {
                // Логируем ошибку, если что-то пошло не так при первичной вставке
                e.printStackTrace()
            }
        }
    }
}