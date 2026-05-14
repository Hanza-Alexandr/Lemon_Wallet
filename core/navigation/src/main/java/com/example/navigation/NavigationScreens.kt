package com.example.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class ManagerScreens {
    @Serializable
    data object OnBoarding: ManagerScreens()
    @Serializable
    data object AuthorizationScreen: ManagerScreens()
    @Serializable
    data object MainManagerScreens: ManagerScreens()
    @Serializable
    data class EditStorage(val storageId: Long): ManagerScreens()
    @Serializable
    object CreateStorage: ManagerScreens()
}