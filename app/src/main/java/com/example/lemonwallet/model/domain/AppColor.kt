package com.example.lemonwallet.model.domain

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.lemonwallet.model.state.DomainState

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

class NewColor private constructor(
    override val hexCode: String,
    override val owner: Owner.User

): AppColor(){
    companion object{
        fun create(hexCode: String, structure: Owner.User): DomainState<NewColor> {
            if (!isValidHex(hexCode)) return DomainState.Error("❌Ошибка при создании объекта. Некорректный Hex")
            return DomainState.Success(NewColor(hexCode,structure))
        }
    }
}