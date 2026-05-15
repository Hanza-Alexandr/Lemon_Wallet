package com.example.navigation

import kotlinx.coroutines.flow.SharedFlow

interface INavigator {
    val navigationActions: SharedFlow<NavigationAction>
    fun navigateTo(route: NavigationRoute, popUpTo: NavigationRoute? = null, inclusive: Boolean = false)
    fun goBack()
}

