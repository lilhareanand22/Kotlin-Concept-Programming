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

    names
        .combine(marks) { name, mark ->
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

    numbers.combine(letters) { number, letter ->
        "$letter -> $number"

    }.collect {
        println(it)
    }

    println("----------------- Third Example --------")

    val flow1 = flowOf(1,2)

    val flow2 = flowOf("A","B","C")

    flow1

        .combine(flow2) { a, b ->

            "$a$b"

        }

        .collect(::println)
}