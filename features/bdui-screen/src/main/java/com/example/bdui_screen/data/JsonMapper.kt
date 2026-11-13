package com.example.bdui_screen.data

import com.example.bdui_screen.domain.BDUIComponent
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

class JsonMapper {
}

val bduiJsonConfig = Json{
    ignoreUnknownKeys = true
    classDiscriminator = "type"

    serializersModule = SerializersModule {
        polymorphic(BDUIComponent::class) {
            // Если приходит неизвестный "type", направляем его в класс Unknows.
            // Парсер возьмет из JSON поле "id", а всё остальное проигнорирует.
            defaultDeserializer { BDUIComponent.Unknows.serializer() }
        }
    }
}