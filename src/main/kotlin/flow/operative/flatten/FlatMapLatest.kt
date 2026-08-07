package flow.operative.flatten

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    flowOf(1, 2, 3)

        .flatMapLatest { number ->

            flow {

                println("Start $number")

                emit(number)

                delay(1000)

                emit(number * 10)

            }

        }

        .collect(::println)
}