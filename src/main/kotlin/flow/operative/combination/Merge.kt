package flow.operative.combination

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val flow1 = flowOf(1, 2, 3)

    val flow2 = flowOf(10, 20, 30)

    merge(flow1, flow2)

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

        delay(500)

        emit("B")

        delay(500)

        emit("C")

    }
    merge(numbers, letters).collect(::println)
}