package com.example.last_operation_block.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.domain.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

object TestData {
    val sampleColorBlue = SystemColor(id = 1, hex = "#2196F3")
    val sampleColorGreen = SystemColor(id = 2, hex = "#4CAF50")
    val sampleColorRed = SystemColor(id = 3, hex = "#F44336")
    val sampleColorOrange = SystemColor(id = 4, hex = "#FF9800")

    val storageCard = Storage(
        id = 1,
        name = "Сбербанк",
        userId = 1,
        currency = Currency.RUB,
        typeStorage = TypeStorage.CARD,
        note = "Зарплатная",
        color = sampleColorBlue,
        isStatistics = true,
        isArchive = false
    )

    val storageCash = Storage(
        id = 2,
        name = "Наличные",
        userId = 1,
        currency = Currency.RUB,
        typeStorage = TypeStorage.CASH,
        note = "В кармане",
        color = sampleColorOrange,
        isStatistics = true,
        isArchive = false
    )

    val categoryFood = Category(
        id = 1,
        name = "Еда и напитки",
        color = sampleColorGreen,
        icon = android.R.drawable.ic_menu_report_image.toString(),
        need = NeedCategory.MUST_HAVE,
        isHidden = false,
        owner = Owner.System,
        structure = CategoryStructure.Root
    )

    val categorySalary = Category(
        id = 2,
        name = "Зарплата",
        color = sampleColorBlue,
        icon = android.R.drawable.ic_input_add.toString(),
        need = NeedCategory.OPTIONAL,
        isHidden = false,
        owner = Owner.System,
        structure = CategoryStructure.Root
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val debitTransaction = DebitTransaction(
        id = 1,
        storage = storageCard,
        category = categorySalary,
        amount = BigDecimal("75000.00"),
        time = LocalTime.of(10, 15),
        date = LocalDate.now(),
        status = StatusOperation.CONFIRMED,
        note = "Аванс за май"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val creditTransaction = CreditTransaction(
        id = 2,
        storage = storageCard,
        category = categoryFood,
        amount = BigDecimal("1240.00"),
        time = LocalTime.of(19, 30),
        date = LocalDate.now(),
        status = StatusOperation.CONFIRMED,
        note = "Продукты домой"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val transferTransaction = TransferTransaction(
        id = 3,
        fromStorage = storageCard,
        toStorage = storageCash,
        amount = BigDecimal("5000.00"),
        date = LocalDate.now(),
        time = LocalTime.of(14, 0),
        status = StatusOperation.CONFIRMED,
        note = "Снятие наличных в банкомате"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val testOperations: List<Operation> = listOf(
        creditTransaction,
        debitTransaction,
        transferTransaction,
        debitTransaction.copy(id = 4, amount = BigDecimal("450.00"), note = "Кофе"),
        transferTransaction.copy(id = 5, amount = BigDecimal("1000.00"), fromStorage = storageCash, toStorage = storageCard, note = "Положил на карту")
    )
}
