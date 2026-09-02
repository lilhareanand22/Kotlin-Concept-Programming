package flow.operative.flatten

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
//Unlike flatMapConcat (which forces a strict, one-at-a-time queue),
//flatMapMerge fires off all inner flows at the same time and emits their
//results as soon as they complete, regardless of the order they were started in.
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