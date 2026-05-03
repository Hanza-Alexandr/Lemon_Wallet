package com.example.domain

sealed class CategoryStructure {
    object Root : CategoryStructure()
    data class Child(val parentId: String) : CategoryStructure()
}