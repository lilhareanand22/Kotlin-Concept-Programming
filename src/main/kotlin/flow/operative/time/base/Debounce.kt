package flow.operative.time.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    flow {

        emit("A")

        delay(100)

        emit("An")

        delay(100)

        emit("Ana")

        delay(100)

        emit("Anand")

    }

        .debounce(300)

        .collect(::println)

    println("----------------- Second Example --------")
    flow {

        emit(1)

        delay(100)

        emit(2)

        delay(100)

        emit(3)

        delay(600)

        emit(4)

    }

        .debounce(300)

        .collect(::println)

}