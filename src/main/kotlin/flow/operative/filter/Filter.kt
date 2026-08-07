package flow.operative.filter

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(1, 2, 3, 4, 5, 6)
        .filter { number ->
            number % 2 == 0
        }
        .collect {
            println(it)
        }
    println("----------------- Second Example --------")
    flowOf(
        Person("John", 15),
        Person("Alex", 25),
        Person("Sam", 17),
        Person("David", 30)
    )
        .filter {
            it.age >= 18
        }
        .collect {
            println(it)
        }
    println("----------------- Second Example --------")
    flowOf(
        "Kotlin",
        "C",
        "Java",
        "AI"
    )
        .filter {
            it.length > 3
        }
        .collect(::println)

}

data class Person(
    val name: String,
    val age: Int
)