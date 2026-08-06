package collection.ordering

fun main() {
    val numbers = listOf("one", "two", "three", "four")

    val sortedNumbers = numbers.sortedBy { it.length}
    println("Sorted by length ascending: $sortedNumbers")
    val sortedByLast = numbers.sortedByDescending { it.length}
    println("Sorted by the last letter descending: $sortedByLast")
}