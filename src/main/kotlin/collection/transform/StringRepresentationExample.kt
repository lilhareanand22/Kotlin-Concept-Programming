package collection.transform

fun main() {
    val numbers = listOf("one", "two", "three", "four")

    println(numbers)
    println(numbers.joinToString())

    val listString = StringBuffer("The list of numbers: ")
    numbers.joinTo(listString)
    println(listString)

    val numbers1 = listOf("one", "two", "three", "four")
    println(numbers1.joinToString(separator = " | ", prefix = "start: ", postfix = ": end"))

    val numbers2 = (1..100).toList()
    println(numbers2.joinToString(limit = 10, truncated = "<...>"))
}