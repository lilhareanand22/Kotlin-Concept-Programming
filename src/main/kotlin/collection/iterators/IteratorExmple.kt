package collection.iterators

fun main() {
    val numbers = listOf("one", "two", "three", "four")
    val numbersIterator = numbers.iterator()
    while (numbersIterator.hasNext()) {
        println(numbersIterator.next())
    }
    //For Loop
    val number1 = listOf("one", "two", "three", "four")
    for (item in number1)
    {
        println(item)
        // one
        // two
        // three
        // four
    }
    //For each
    numbers.forEach {
        println(it)
        // one
        // two
        // three
        // four
    }
}