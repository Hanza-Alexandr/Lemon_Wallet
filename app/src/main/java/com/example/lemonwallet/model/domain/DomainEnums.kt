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