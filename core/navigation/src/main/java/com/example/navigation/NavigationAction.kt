package com.example.navigation

sealed class NavigationAction{
    data class To(val route: NavigationRoute, val popUpTo: NavigationRoute? = null, val inclusive: Boolean = false): NavigationAction()
    object Back: NavigationAction()
}