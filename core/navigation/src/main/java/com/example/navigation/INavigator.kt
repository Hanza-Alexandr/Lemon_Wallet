package com.example.navigation

import kotlinx.coroutines.flow.SharedFlow

interface INavigator {
    val navigationActions: SharedFlow<NavigationAction>
    fun navigateTo(route: NavigationRoute, popUpTo: NavigationRoute? = null, inclusive: Boolean = false)
    fun goBack()
}

sealed class NavigationAction{

    data class To(val route: NavigationRoute, val popUpTo: NavigationRoute? = null, val inclusive: Boolean = false): NavigationAction()
    object Back: NavigationAction()
}