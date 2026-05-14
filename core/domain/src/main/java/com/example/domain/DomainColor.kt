package com.example.domain

abstract class DomainColor {
    abstract val hex: String
}

abstract class ExistColor : DomainColor() {
    abstract val id: Long
    abstract val userId: Long?
}

//Системный цвет. Из БД
class SystemColor(
    override val id: Long,
    override val hex: String,
): ExistColor(){
    override val userId: Long? = null
}

// Пользовательский цвет: привязан к юзеру . ИЗ БД
class UserColor(
    override val id: Long,
    override val hex: String,
    override val userId: Long
): ExistColor()

// Новые пользовательский цвет или новый для БД системный цвет
class NewColor(override val hex: String): DomainColor()


fun ExistColor.toUiState(): ColorUIState{
    return ColorUIState.DataBaseColor(this)
}
/**
abstract class DomainColor {
abstract val hex: String
}
fun DomainColor.toComposeColor(): Color{
try {
return Color(this.hex.toColorInt())
}
catch (e: IllegalArgumentException){
throw IllegalArgumentException("❌Ошибка при конвертации цвета в Color: ${e.message}")
}
}

abstract class ExistColor : DomainColor() {
abstract val id: Long
abstract val userId: Long?
}

fun ExistColor.toUiState(): ColorUIState.DataBaseColor{
return ColorUIState.DataBaseColor(this)
}

//Системный цвет. Из БД
class SystemColor(
override val id: Long,
override val hex: String,
): ExistColor(){
override val userId: Long? = null
}

// Пользовательский цвет: привязан к юзеру . ИЗ БД
class UserColor(
override val id: Long,
override val hex: String,
override val userId: Long
): ExistColor()

// Новые пользовательский цвет или новый для БД системный цвет
class NewColor(override val hex: String): DomainColor()
 */