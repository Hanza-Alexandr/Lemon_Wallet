package com.example.lemonwallet

import com.example.navigation.INavigator
import com.example.navigation.NavigationAction
import com.example.navigation.NavigationRoute
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Singleton
class NavigatorImpl @Inject constructor() : INavigator {
    private val _actions = MutableSharedFlow<NavigationAction>(extraBufferCapacity = 1)
    override val navigationActions = _actions.asSharedFlow()

    override fun navigateTo(route: NavigationRoute, popUpTo: NavigationRoute?, inclusive: Boolean) {
        _actions.tryEmit(NavigationAction.To(route, popUpTo, inclusive))
    }

    override fun goBack() {
        _actions.tryEmit(NavigationAction.Back)
    }
}