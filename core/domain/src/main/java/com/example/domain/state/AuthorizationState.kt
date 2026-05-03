package com.example.domain.state

/**
 * Закрытый, защищенный класс состояния авторизации.
 */
sealed class AuthorizationState{
    data class Authorization(val id: Int): AuthorizationState()
    object Guest: AuthorizationState()
    object NoAuthorization: AuthorizationState()
}