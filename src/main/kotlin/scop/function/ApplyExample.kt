package scop.function

// The context object is available as a receiver (this).

// The return value is the object itself.

data class Person(
    var name: String,
    var age: Int = 0,
    var city: String = ""
)

fun main() {
    val adam = Person("Adam").apply {
        age = 32
        city = "London"
    }
    println(adam)
}
