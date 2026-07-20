package collection.iterators

fun main() {
    val numbers = mutableListOf("one", "two", "three-1", "four")
   // val mutableIterator = numbers.iterator()
    val mutableIterator = numbers.listIterator()
    mutableIterator.next()
    mutableIterator.remove()
    println("After removal: $numbers")

    mutableIterator.next()
    mutableIterator.add("two")
    println(numbers)
// [one, two, four, four]
    mutableIterator.next()
    mutableIterator.set("three")
    println(numbers)
}