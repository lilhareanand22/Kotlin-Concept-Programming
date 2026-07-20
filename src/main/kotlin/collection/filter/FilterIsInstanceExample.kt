package collection.filter

fun main() {
    val numbers = listOf(null, 1, "two", 3.0, "four")
    println("All String elements in upper case:")
    numbers.filterIsInstance<String>().forEach {
        println(it.uppercase())
    }
    val numbers1 = listOf(null, "one", "two", null)
    numbers1.filterNotNull().forEach {
        println(it.length)   // length is unavailable for nullable Strings
    }
}