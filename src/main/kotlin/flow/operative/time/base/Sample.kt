package flow.operative.time.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    flow {

        emit(1)

        delay(100)

        emit(2)

        delay(100)

        emit(3)

        delay(100)

        emit(4)

        delay(100)

        emit(5)

    }

        .sample(250)

        .collect(::println)

}