package com.example.lemonwallet.model.service

import androidx.compose.foundation.gestures.forEach
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.ExistColor
import com.example.lemonwallet.model.domain.NewColor
import com.example.lemonwallet.model.domain.UserColor
import com.example.lemonwallet.model.repository.IColorRepository
import com.example.lemonwallet.ui.view.state.ColorUIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.forEach
import kotlin.text.equals


class ColorService @Inject constructor(private val colorRepo: IColorRepository) {
    fun getUserColorFlow(): Flow<List<UserColor>>{
        return colorRepo.getAllFlow().map { list ->
            // оставляем только те, которые являются экземплярами UserColor
            list.filterIsInstance<UserColor>()
        }
    }

    private fun getAllColorFlow(): Flow<List<ExistColor>>{
        return colorRepo.getAllFlow()
    }

    suspend fun save(color: ColorUIState.LocalSystemColor): ExistColor?{
        return colorRepo.save(NewColor(color.color.hexCode))
    }
    suspend fun save(color: NewColor): ExistColor?{
        return colorRepo.save(color)
    }

    val colorListForPicker: Flow<List<ColorUIState>> = getAllColorFlow().map { dbColors ->
        // 1. Создаем начальный список из всех системных цветов (Enum)
        val resultList: MutableList<ColorUIState> = EnumColor.entries.map {
            ColorUIState.LocalSystemColor(it)
        }.toMutableList()

        // 2. Итерируемся по цветам из БД и обновляем список
        dbColors.forEach { dbColor ->
            val dbHex = dbColor.hex

            // Ищем, есть ли такой цвет уже в списке (по HEX)
            val index = resultList.indexOfFirst {
                when(it){
                    is ColorUIState.DataBaseColor ->  it.color.hex.equals(dbHex, ignoreCase = true)
                    is ColorUIState.LocalSystemColor -> it.color.hexCode.equals(dbHex, ignoreCase = true)
                }

            }
            if (index != -1) {
                // Если нашли совпадение, заменяем системный цвет на версию из БД
                resultList[index] = ColorUIState.DataBaseColor(dbColor)
            } else {
                // Если такого цвета в Enum нет, просто добавляем его в конец (пользовательский цвет)
                resultList.add(ColorUIState.DataBaseColor(dbColor))
            }
        }

        // 3. Возвращаем итоговый список
        resultList.toList()
    }

}