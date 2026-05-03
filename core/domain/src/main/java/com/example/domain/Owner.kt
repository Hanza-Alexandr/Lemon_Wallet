package com.example.domain

sealed class Owner {
    object System : Owner()
    data class User(val userId: Long) : Owner()
}