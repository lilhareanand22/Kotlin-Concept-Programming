package scop.function

// The context object is available as a receiver
// The return value is the lambda result

fun main() {
    val number = mutableListOf("one", "two", "three")
    with(number) {
        println("'with' is called with argument $this")
        println("It contain $size element")
    }

    val firstAndLast = with(number) {
        "The first element is ${first()}," + " the last element is ${last()}"
    }
    println(firstAndLast)
}