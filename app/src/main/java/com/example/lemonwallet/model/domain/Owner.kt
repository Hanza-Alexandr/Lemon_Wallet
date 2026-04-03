package com.example.lemonwallet.model.domain

sealed class Owner {
    object System : Owner()
    data class User(val userId: Long) : Owner()
}