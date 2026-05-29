package com.example.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    @Serializable
    data object OnBoarding: NavigationRoute()
    @Serializable
    data object AuthorizationScreen: NavigationRoute()
    @Serializable
    data object MainScreen: NavigationRoute()
    @Serializable
    data class EditStorage(val storageId: String): NavigationRoute()
    @Serializable
    object CreateStorage: NavigationRoute()
    @Serializable
    data class CreateOperation(val storageId: String): NavigationRoute()
    @Serializable
    data class EditOperation(val operationId: String, val isTransfer: Boolean): NavigationRoute()
    @Serializable
    object CategorySelectScreen: NavigationRoute()
}
