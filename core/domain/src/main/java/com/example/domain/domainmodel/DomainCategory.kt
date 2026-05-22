package com.example.domain.domainmodel


data class DomainCategory(
    val id: String,
    val userId: String,
    val name: String,
    val color: DomainColor?,
    val icon: String,
    val parenId: String? //Поч не категория - пот проще потом проще получить из базы список подкатегорий чем хранить дерево тут. А id для изменения хотя думаю можно в будущем убрать
)
data class NewDomainCategory(
    val userId: String,
    val name: String,
    val color: DomainColor?,
    val icon: String,
    val parenId: String?
)
