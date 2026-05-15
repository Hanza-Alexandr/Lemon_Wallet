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
    private val _actions = MutableSharedFlow<NavigationAction>(extraBufferCapacity = 1)//Поток - "радиостанция" не требует начального значения и работает в не зависимости от наличия подписчиков, и не перезапускается при появлении новых подписчиков
    override val navigationActions = _actions.asSharedFlow()

    override fun navigateTo(
        route: NavigationRoute,//Целевой экран
        popUpTo: NavigationRoute?,// Нужно ли очистить стек до определенного экрана
        inclusive: Boolean // Очищать ли указанный выше экран включительно
    ) {
        _actions.tryEmit(NavigationAction.To(route, popUpTo, inclusive))
    }

    override fun goBack() {
        _actions.tryEmit(NavigationAction.Back)
    }
}

/**
 * Объявляем приватный изменяемы поток "радиостанцию" action
 * в которую через методы to и back посылаем команды навигации
 * классы работающие с этм объектом подключаются к потоку(только для чтения) navigationAction и слушают изменения
 */