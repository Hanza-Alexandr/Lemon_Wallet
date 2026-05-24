package com.example.domain.usecase

import net.objecthunter.exp4j.ExpressionBuilder
import javax.inject.Inject

class CalculateExpressionUseCase @Inject constructor() {

    fun execute(currentExpression: String, keyPressed: String): CalculatorResult {
        val newExpression = when (keyPressed) {
            "AC" -> ""
            "⌫" -> if (currentExpression.isNotEmpty()) currentExpression.dropLast(1) else ""
            "( )" -> handleParentheses(currentExpression)
            "=" -> {
                val res = evaluateToLong(currentExpression)
                if (res != 0L) {
                    // Преобразуем Long (копейки) обратно в строку для поля ввода
                    val integerPart = res / 100
                    val fractionalPart = res % 100
                    if (fractionalPart == 0L) {
                        integerPart.toString()
                    } else {
                        // Формируем дробную часть, убирая лишние нули в конце (например, 1.50 -> 1.5)
                        val fractionStr = fractionalPart.toString().padStart(2, '0').trimEnd('0')
                        "$integerPart.$fractionStr"
                    }
                } else currentExpression
            }
            else -> appendKey(currentExpression, keyPressed)
        }

        return CalculatorResult(
            expression = newExpression,
            amount = evaluateToLong(newExpression)
        )
    }

    private fun appendKey(current: String, key: String): String {
        val operators = listOf("+", "−", "×", "÷", "%")
        val lastChar = current.lastOrNull()?.toString() ?: ""

        // 1. Если строка пустая
        if (current.isEmpty()) {
            if (key in operators) return "0$key"
            if (key == ".") return "0." // Нажали точку в начале -> "0."
            if (key == "0") return "0"
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
            // Ищем последнее число в строке (может быть после оператора или скобки)
            val lastNumber = current.split(*operators.toTypedArray(), "(", ")").last()
            if (lastNumber.contains(".")) return current
            if (lastNumber.isEmpty()) return current + "0." // Если нажали точку после оператора -> "0."
            return "$current."
        }
        // 3. Если вводим точку
        if (key == ".") {
            if (lastChar == ".") return current
            // Ищем последнее число в строке (может быть после оператора или скобки)
            val lastNumber = current.split(*operators.toTypedArray(), "(", ")").last()
            if (lastNumber.contains(".")) return current
            if (lastNumber.isEmpty()) return current + "0." // Если нажали точку после оператора -> "0."
            return current + "."
        }

        // 4. Ограничение нулей
        // 5. Замена одиночного нуля на цифру
        val lastNumber = current.split(*operators.toTypedArray(), "(", ")").last()
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
    private fun evaluateToLong(expression: String): Long {
        if (expression.isEmpty()) return 0L

        return try {
            val prepared = expression
                .replace("×", "*")
                .replace("÷", "/")
                .replace("−", "-")

            val e = ExpressionBuilder(prepared).build()
            val res = e.evaluate()

            // Переводим в копейки: 1.2345 -> 123.45 -> 123
            (res * 100).toLong()
        } catch (e: Exception) {
            0L
        }
    }
}

data class CalculatorResult(
    val expression: String,
    val amount: Long
)