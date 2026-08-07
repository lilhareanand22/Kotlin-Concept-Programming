package flow.operative.combination

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val names = flowOf(
        "John",
        "Alex",
        "Sam"
    )

    val marks = flowOf(
        80,
        90,
        70
    )

    names.zip(marks) { name, mark ->

            "$name -> $mark"

        }

        .collect(::println)

    println("----------------- Second Example --------")

    val numbers = flow {

        emit(1)

        delay(1000)

        emit(2)

        delay(1000)

        emit(3)

    }

    val letters = flow {

        delay(500)

        emit("A")

        delay(2000)

        emit("B")

        emit("C")

    }

    numbers.zip(letters) { number, letter ->
        "$letter -> $number"

    }.collect {
        println(it)
    }
}