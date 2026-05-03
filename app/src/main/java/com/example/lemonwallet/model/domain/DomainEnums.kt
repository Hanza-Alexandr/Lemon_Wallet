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
}

enum class SystemCategory(parentCategory: SystemCategory?, name: String){
    // === ЕДА, ПРОДУКТЫ, ОБЩЕПИТ ===
    FOOD_AND_DINING(null, "Еда, продукты, общепит"),
        GROCERIES(FOOD_AND_DINING, "Еда, напитки, продукты"),
        RESTAURANTS_CAFES(FOOD_AND_DINING, "Рестораны, кафе"),

    // === ПОКУПКИ ===
    PURCHASES(null, "Покупки"),
        PHARMACY(PURCHASES, "Аптека"),
        KIDS(PURCHASES, "Дети"),
        HOME_GARDEN(PURCHASES, "Дом и сад"),
        BEAUTY_CARE(PURCHASES, "Красота и уход"),
        PETS(PURCHASES, "Домашние животные"),
        GIFTS(PURCHASES, "Подарки"),
        ELECTRONICS(PURCHASES, "Электроника, аксессуары"),
        CLOTHES_SHOES(PURCHASES, "Одежда и обувь"),

    // === ЖИЛЬЕ ===
    HOUSING(null, "Жилье"),
        RENT(HOUSING, "Аренда"),
        MORTGAGE(HOUSING, "Ипотека"),
        UTILITIES(HOUSING, "ЖКХ"),
        REPAIR(HOUSING, "Ремонт"),
        HOUSING_OTHER(HOUSING, "Прочие расходы"),
        HOUSEHOLD_SERVICES(HOUSING, "Бытовые услуги"),

    // === ТРАНСПОРТ ===
    TRANSPORT(null, "Транспорт"),
        PUBLIC_TRANSPORT(TRANSPORT, "Общественный транспорт"),
        TAXI_CARSHARING(TRANSPORT, "Такси, каршеринг"),

    // === ЛИЧНЫЙ ТРАНСПОРТ ===
    PERSONAL_VEHICLE(null, "Личный транспорт"),
        FUEL_CHARGING(PERSONAL_VEHICLE, "Топливо и зарядка"),
        VEHICLE_MAINTENANCE(PERSONAL_VEHICLE, "Техническое обслуживание"),
        VEHICLE_FINES(PERSONAL_VEHICLE, "Штрафы"),
        VEHICLE_TAX_INSURANCE(PERSONAL_VEHICLE, "Налоги и страхование"),
        VEHICLE_OTHER(PERSONAL_VEHICLE, "Прочие расходы"),
        PARKING(PERSONAL_VEHICLE, "Парковка"),

    // === ЖИЗНЬ И РАЗВЛЕЧЕНИЯ ===
    LIFE_ENTERTAINMENT(null, "Жизнь и развлечения"),
        BEAUTY_SERVICES(LIFE_ENTERTAINMENT, "бьюти-услуги"),
        CULTURE_SPORT(LIFE_ENTERTAINMENT, "Культурные и спортивные мероприятия"),
        GAMBLING(LIFE_ENTERTAINMENT, "Лотереи, азартные игры"),
        EDUCATION(LIFE_ENTERTAINMENT, "Образование, курсы, развитие"),
        SPORT(LIFE_ENTERTAINMENT, "Спорт"),
        TRAVEL_HOTELS(LIFE_ENTERTAINMENT, "Поездки, отели"),
        HOBBY(LIFE_ENTERTAINMENT, "Хобби"),

    // === ВРЕДНЫЕ ПРИВЫЧКИ ===
    BAD_HABITS(null, "Вредные привычки"),
        ALCOHOL(BAD_HABITS, "Алкоголь"),
        TOBACCO_NICOTINE(BAD_HABITS, "Табак и никотин"),

    // === СВЯЗЬ И ИНТЕРНЕТ ===
    COMMUNICATION_INTERNET(null, "Связь и интернет"),
        SUBSCRIPTIONS(COMMUNICATION_INTERNET, "Подписки"),
        MOBILE_CONNECTION(COMMUNICATION_INTERNET, "Мобильная связь"),
        INTERNET(COMMUNICATION_INTERNET, "Интернет"),

    // === ФИНАНСОВЫЕ РАСХОДЫ ===
    FINANCIAL_EXPENSES(null, "Финансовые расходы"),
        TAXES(FINANCIAL_EXPENSES, "Налоги"),
        LOANS_CREDITS(FINANCIAL_EXPENSES, "Кредит, займы, пени по кредитам"),
        INSURANCE(FINANCIAL_EXPENSES, "Страхование"),
        CONTRIBUTIONS(FINANCIAL_EXPENSES, "Взносы"),
        FIN_FINES(FINANCIAL_EXPENSES, "Штрафы"),
        CONSULTATIONS(FINANCIAL_EXPENSES, "Консультации"),
        ALIMONY_EXPENSE(FINANCIAL_EXPENSES, "Алименты"),

    // === ИНВЕСТИЦИИ ===
    INVESTMENTS(null, "Инвестиции"),
        SAVINGS_DEPOSITS(INVESTMENTS, "Сбережения и вклады"),
        FINANCIAL_INVESTMENTS(INVESTMENTS, "Финансовые инвестиции"),
        REAL_ESTATE_INVEST(INVESTMENTS, "Недвижимость"),

    // === ДОХОД ===
    INCOME(null, "Доход"),
        ALIMONY_INCOME(INCOME, "Алименты"),
        REFUNDS(INCOME, "Возврат денег(налоговый вычет, покупка)"),
        RENTAL_INCOME(INCOME, "Аренда"),
        SALARY_BONUS(INCOME, "Зарплата, премия и бонусы"),
        SIDE_JOB_FREELANCE(INCOME, "Подработка и фриланс"),
        GOV_PAYMENTS(INCOME, "Гос. выплаты, пенсия, субсидии"),
        GIFTS_RECEIVED(INCOME, "Подарки"),
        SALE_PROCEEDS(INCOME, "Продажа"),
        DIVIDENDS_INTEREST(INCOME, "Дивиденды")


}