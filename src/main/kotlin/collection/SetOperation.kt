package collection


fun main() {
   val numbers = setOf("One", "Two", "Three")
    // output according to the order
    println(numbers union setOf("four", "five"))

    // same output
    println(numbers intersect setOf("Two", "One"))

    println(numbers subtract setOf("three", "four"))

}