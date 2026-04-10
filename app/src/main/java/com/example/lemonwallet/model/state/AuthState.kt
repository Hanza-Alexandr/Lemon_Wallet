package com.example.lemonwallet.model.state

sealed class AuthState{
    object Loading: AuthState()
    data class Auth(val id: Int): AuthState()
    object Guest: AuthState()
    object NoAuth: AuthState()
}