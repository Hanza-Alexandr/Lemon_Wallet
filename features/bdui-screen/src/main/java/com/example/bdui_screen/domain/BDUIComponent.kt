package com.example.bdui_screen.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BDUIResponse(val components:List<BDUIComponent>)

@Serializable
sealed interface BDUIComponent{
    val id: String

    //Структурныйе компоненты
    @Serializable
    @SerialName("Spacer")
    data class Spacer(override val id: String, val dp: Int): BDUIComponent

    @Serializable
    @SerialName("Divider")
    data class Divider(override val id: String): BDUIComponent

    @Serializable
    @SerialName("HorizontalCarousel")
    data class HorizontalCarousel(override val id: String, val components: List<BDUIComponent>): BDUIComponent

    @Serializable
    @SerialName("VerticalCarousel")
    data class VerticalCarousel(override val id: String, val components: List<BDUIComponent>): BDUIComponent

    //Типографика
    @Serializable
    @SerialName("HeaderBlock")
    data class HeaderBlock(override val id: String, val header: String): BDUIComponent

    @Serializable
    @SerialName("TextBlock")
    data class TextBlock(override val id: String, val text: String): BDUIComponent

    @Serializable
    @SerialName("ImageBlock")
    data class ImageBlock(override val id: String, val url: String): BDUIComponent

    //Действия
    @Serializable
    @SerialName("ActionArea")
    data class ActionArea(override val id: String, val action: BDUIAction, val component: BDUIComponent): BDUIComponent

    //Новые фичи которые сделал бек но неизвестн в клиенте
    @Serializable
    data class Unknows(override val id: String): BDUIComponent
}

@Serializable
sealed interface BDUIAction{
    //Открыть ссылку
    @Serializable
    @SerialName("OpenLink")
    data class OpenLink(val uri: String): BDUIAction

    //Навигация по приложению, октрытие экрана
    @Serializable
    @SerialName("NavigateRoute")
    data class NavigateRoute(val route: String): BDUIAction

    //Копирование в буфер обмена
    @Serializable
    @SerialName("CopyToClipboard")
    data class CopyToClipboard(val text: String): BDUIAction
}