package com.example.lemonwallet.model.domain

enum class TypeOperation{
    DEBIT,
    CREDIT;
}

enum class AppThem{
    DARK,
    LIGHT,
    SYSTEM;
}
enum class TypeStorage {
    GENERAL,
    BANK_ACCOUNT,
    CASH,
    CARD;
}
enum class Currency{
    RUB,
    USD;

}
enum class NeedCategory{
    MUST_HAVE,
    OPTIONAL;
}
enum class StatusOperation{
    CONFIRMED,
    NOT_CONFIRMED;
}
enum class EnumColor(val hexCode: String) {
    BLUE("#2196F3"),
    RED("#F44336"),
    GREEN("#4CAF50"),
    YELLOW("#FFEB3B"),
    PURPLE("#9C27B0"),
    ORANGE("#FF9800"),
    GREY("#9E9E9E"),
    BLACK("#000000"),
    WHITE("#FFFFFF")
}