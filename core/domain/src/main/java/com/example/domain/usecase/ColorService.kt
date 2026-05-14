package com.example.domain.usecase

import com.example.domain.ColorUIState
import com.example.domain.EnumColor
import com.example.domain.ExistColor
import com.example.domain.IColorRepository
import com.example.domain.NewColor
import com.example.domain.UserColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ColorService @Inject constructor(private val colorRepo: IColorRepository) {
    fun getUserColorFlow(): Flow<List<UserColor>>{
        return colorRepo.getAllFlow().map { list ->
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

    suspend fun delete(color: UserColor) {
        colorRepo.delete(color)
    }

    val colorListForPicker: Flow<List<ColorUIState>> = getAllColorFlow().map { dbColors ->
        val resultList: MutableList<ColorUIState> = EnumColor.entries.map {
            ColorUIState.LocalSystemColor(it)
        }.toMutableList()

        dbColors.forEach { dbColor ->
            val dbHex = dbColor.hex

            val index = resultList.indexOfFirst {
                when(it){
                    is ColorUIState.DataBaseColor ->  it.color.hex.equals(dbHex, ignoreCase = true)
                    is ColorUIState.LocalSystemColor -> it.color.hexCode.equals(dbHex, ignoreCase = true)
                }

            }
            if (index != -1) {
                resultList[index] = ColorUIState.DataBaseColor(dbColor)
            } else {
                resultList.add(ColorUIState.DataBaseColor(dbColor))
            }
        }

        resultList.toList()
    }

}
