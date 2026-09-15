package interview.program

import java.util.Stack

fun precedence(operator: Char): Int {
    return when (operator) {
        '+', '-' -> 1
        '*', '/' -> 2
        else -> 0
    }
}

fun calculate(left: Double, operator: Char, right: Double): Double {
    return when (operator) {
        '+' -> left + right
        '-' -> left - right
        '*' -> left * right
        '/' -> {
            if (right == 0.0) {
                throw ArithmeticException("Cannot divide by zero")
            }
            left / right
        }
        else -> throw IllegalArgumentException("Invalid operator")
    }
}

fun evaluate(expression: String): Double {

    val numbers = Stack<Double>()
    val operators = Stack<Char>()

    var i = 0

    while (i < expression.length) {

        // Ignore spaces
        if (expression[i].isWhitespace()) {
            i++
            continue
        }

        // Read number
        if (expression[i].isDigit()) {

            var number = ""

            while (
                i < expression.length &&
                (expression[i].isDigit() || expression[i] == '.')
            ) {
                number += expression[i]
                i++
            }

            numbers.push(number.toDouble())
            continue
        }

        // Read operator
        val currentOperator = expression[i]

        if (currentOperator == '+' ||
            currentOperator == '-' ||
            currentOperator == '*' ||
            currentOperator == '/'
        ) {

            while (
                operators.isNotEmpty() &&
                precedence(operators.peek()) >=
                precedence(currentOperator)
            ) {

                val operator = operators.pop()

                val right = numbers.pop()
                val left = numbers.pop()

                val result = calculate(left, operator, right)

                numbers.push(result)
            }

            operators.push(currentOperator)
        }

        i++
    }

    // Process remaining operators
    while (operators.isNotEmpty()) {

        val operator = operators.pop()

        val right = numbers.pop()
        val left = numbers.pop()

        val result = calculate(left, operator, right)

        numbers.push(result)
    }

    return numbers.pop()
}

fun main() {

    val expression = "10 + 20 * 2"

    val result = evaluate(expression)

    println("Expression: $expression")
    println("Result: $result")
}