package flow.operative.flatten

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    flowOf(1, 2, 3)

        .flatMapMerge { number ->

            flow {

                emit("Start $number")

                delay(1000)

                emit("End $number")

            }

        }

        .collect(::println)

    println("----------------- Second Example --------")
    flowOf(1,2)

        .flatMapMerge {

            flow {

                emit(it)

                delay(1000)

                emit(it*10)

            }

        }

        .collect(::println)
}