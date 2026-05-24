package com.example.domain.usecase

import net.objecthunter.exp4j.ExpressionBuilder
import javax.inject.Inject

class CalculateExpressionUseCase @Inject constructor() {

    fun execute(currentExpression: String, keyPressed: String): CalculatorResult {
        val newExpression = when (keyPressed) {
            "AC" -> ""
            "⌫" -> if (currentExpression.isNotEmpty()) currentExpression.dropLast(1) else ""
            "( )" -> handleParentheses(currentExpression)
            "=" -> evaluate(currentExpression)
            else -> appendKey(currentExpression, keyPressed)         }

        return CalculatorResult(
            expression = newExpression,
            evaluatedResult = if (newExpression.isEmpty()) "0" else evaluate(newExpression)
        )
    }

    private fun appendKey(current: String, key: String): String {
        val operators = listOf("+", "−", "×", "÷", "%")
        val lastChar = current.lastOrNull()?.toString() ?: ""

        // 1. Если строка пустая
        if (current.isEmpty()) {
            if (key in operators || key == ".") return "0$key" // Если начали с +, будет 0+
            if (key == "0") return "0" // Не даем ставить много нулей в начале
            return key
        }

        // 2. Если вводим оператор
        if (key in operators) {
            // Если последний символ тоже оператор — заменяем его
            return if (lastChar in operators) {
                current.dropLast(1) + key
            } else {
                current + key
            }
        }

        // 3. Если вводим точку
        if (key == ".") {
            if (lastChar == ".") return current
            // Проверяем, есть ли уже точка в последнем числе
            val lastNumber = current.split(*operators.toTypedArray()).last()
            if (lastNumber.contains(".")) return current
            return current + key
        }
        // 4. Если вводим ноль
        if (key == "0") {
            // Если текущее число "0", не даем вводить еще нули
            val lastNumber = current.split(*operators.toTypedArray()).last()
            if (lastNumber == "0") return current
        }

        // 5. Если текущее число "0" и вводится цифра (не точка) — заменяем 0 на эту цифру
        val lastNumber = current.split(*operators.toTypedArray()).last()
        if (lastNumber == "0" && key != ".") {
            return current.dropLast(1) + key
        }

        return current + key
    }

        private fun handleParentheses(current: String): String {
        val openCount = current.count { it == '(' }
        val closeCount = current.count { it == ')' }
        val lastChar = current.lastOrNull()

        return when {
            // Если пусто или последний символ оператор/открывающая скобка -> открываем
            current.isEmpty() || lastChar == '(' || lastChar?.toString() in listOf("+", "−", "×", "÷") -> {
                current + "("
            }
            // Если есть незакрытые скобки и последний символ цифра -> закрываем
            openCount > closeCount && (lastChar?.isDigit() == true || lastChar == ')') -> {
                current + ")"
            }
            else -> current + "×(" // В остальных случаях подразумеваем умножение
        }
    }
    private fun evaluate(expression: String): String {
        if (expression.isEmpty()) return ""

        return try {
            // Подготовка строки для exp4j
            val prepared = expression
                .replace("×", "*")
                .replace("÷", "/")
                .replace("−", "-")
                .replace(",", ".")

            val e = ExpressionBuilder(prepared).build()
            val res = e.evaluate()

            // Форматирование: убираем .0 у целых чисел
            if (res % 1.0 == 0.0) {
                res.toLong().toString()
            } else {
                "%.2f".format(res).replace(",", ".")
            }
        } catch (e: Exception) {
            // Если выражение неполное (например "5+"), возвращаем пусто или ошибку
            ""
        }
    }
}

data class CalculatorResult(
    val expression: String,
    val evaluatedResult: String
)