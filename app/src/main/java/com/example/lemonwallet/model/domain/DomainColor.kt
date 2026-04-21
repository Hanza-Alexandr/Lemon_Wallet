package com.example.lemonwallet.model.domain

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.lemonwallet.model.roomdb.entities.ColorRoomEntity
import com.example.lemonwallet.ui.view.state.ColorUIState

/**
abstract class AppColor{
    abstract val hexCode: String
    abstract val owner: Owner
    companion object{
        fun isValidHex(hexCode: String): Boolean{
            val code = hexCode.lowercase()
            return if (code.length == 0) false
            else true
        }
    }

    fun toDomain(): Color{
        return Color(this.hexCode.toColorInt())
    }
}



abstract class ExistColor: AppColor() {
    abstract val id: Long
}

class UserColor private constructor(
    override val id: Long,
    override val hexCode: String,
    user: Owner.User
): ExistColor(){
    override val owner = user
    companion object{
        fun create(id: Long, hexCode: String, user: Owner.User): UserColor{
            if (id<=0) throw IllegalArgumentException("❌Ошибка при создании объекта. Некорректный ID")
            if (!isValidHex(hexCode)) throw IllegalArgumentException("❌Ошибка при создании объекта. Некорректный Hex")
            return UserColor(id,hexCode, user)
        }
    }

    fun hexChange(newHex: String): DomainState<UserColor> {
        return if (!isValidHex(newHex)) DomainState.Error("❌Некорректный HexCode")
        else DomainState.Success(create(id,newHex,owner))
    }
}

class SystemColor private constructor(
    override val id: Long,
    override val hexCode: String
): ExistColor(){
    override val owner: Owner = Owner.System

    companion object{
        fun create(id: Long, hexCode: String): SystemColor{
            if (id<=0) throw IllegalArgumentException("❌Ошибка при создании объекта. Некорректный ID")
            if (!isValidHex(hexCode)) throw IllegalArgumentException("❌Ошибка при создании объекта. Некорректный Hex")
            return SystemColor(id,hexCode)
        }
    }
}
*/
/**
abstract class DomainColor{
    abstract val hex: DomainHex
}

class SystemColor
open class UserColor(
    val id: Long,
    open val userID: Long,
    override val hex: DomainHex
): DomainColor(){

}
class NewColor(
    override val userID: Long,
    override val hex: DomainHex
): UserColor(0,userID, hex){


}

class DomainHex private constructor(val hexCode: String){
    companion object{
        fun create(hexCode: String): DomainHex{
            // 1. Приводим к единому стандарту (например, добавляем # если его нет и делаем UPPERCASE)
            val normalizedHex = if (hexCode.startsWith("#")) hexCode.uppercase() else "#${hexCode.uppercase()}"

            // 2. Регулярное выражение для проверки 6-значного (#RRGGBB) или 8-значного (#AARRGGBB) кода
            val hexRegex = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$".toRegex()

            // 3. Валидация
            if (!normalizedHex.matches(hexRegex)) {
                throw IllegalArgumentException("❌ Некорректный формат Hex-кода: $hexCode. Ожидается формат #RRGGBB или #AARRGGBB")
            }

            return DomainHex(normalizedHex)
        }
    }

    fun toColor(): Color{
        return Color(this.hexCode.toColorInt())
    }
}
*/

abstract class DomainColor {
    abstract val hex: String


    fun toColor(): Color{
        try {
            return Color(this.hex.toColorInt())
        }
        catch (e: IllegalArgumentException){
            throw IllegalArgumentException("❌Ошибка при конвертации цвета в Color: ${e.message}")
        }
    }
}

abstract class ExistColor : DomainColor() {
    abstract val id: Long
    abstract val userId: Long?
    abstract fun toRoomEntityColor(): ColorRoomEntity
    fun toUiState(): ColorUIState.DataBaseColor{
        return ColorUIState.DataBaseColor(this)
    }
}

//Системный цвет. Из БД
class SystemColor(
    override val id: Long,
    override val hex: String,
): ExistColor(){
    override val userId: Long? = null
    override fun toRoomEntityColor(): ColorRoomEntity {
        return ColorRoomEntity(
            id = id,
            userId = null,
            hexCode = hex
        )
    }
}

// Пользовательский цвет: привязан к юзеру . ИЗ БД
class UserColor(
    override val id: Long,
    override val hex: String,
    override val userId: Long
): ExistColor(){
    override fun toRoomEntityColor(): ColorRoomEntity {
        return ColorRoomEntity(
            id = id,
            userId = userId,
            hexCode = hex
        )
    }
}

// Новые пользовательский цвет или новый для БД системный цвет
class NewColor(override val hex: String): DomainColor() {
    fun toRoomEntityColor(userId: Long): ColorRoomEntity {
        return ColorRoomEntity(
            id = 0,
            userId = userId,
            hexCode = hex
        )
    }
}
