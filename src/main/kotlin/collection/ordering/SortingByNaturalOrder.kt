package collection.ordering

fun main() {
    val numbers = listOf("4", "3", "1", "2")

    println("Sorted ascending: ${numbers.sorted()}")
    println("Sorted descending: ${numbers.sortedDescending()}")
}