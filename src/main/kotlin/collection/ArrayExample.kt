package collection

fun main() {
    val numbers = mutableListOf(10,25,30)
    numbers.add(66)
    numbers.removeAt(0)
    println(numbers)
    numbers.forEachIndexed { index, number ->
        println("$index: $number")
    }
}