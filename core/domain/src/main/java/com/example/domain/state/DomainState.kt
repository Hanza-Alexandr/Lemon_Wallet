package com.example.domain.state

/**
 * Файл с классами состояний
 */

/**
 * Закрытый, защищенный класс состояния списка доменных сущностей.
 */
sealed class DomainStateList<out T> {
    data class Success<T>(val domainList: List<T>): DomainStateList<T>()
    object Empty: DomainStateList<Nothing>()
}

/**
 * Закрытый, защищенный класс состояния доменных сущностей.
 */
sealed class DomainState<T> {
    data class Success<T>(val domain: T) : DomainState<T>()
    data class Error<T>(val message: String) : DomainState<T>()
}



