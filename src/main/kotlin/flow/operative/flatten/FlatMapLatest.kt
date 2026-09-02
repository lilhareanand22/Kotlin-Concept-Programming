package flow.operative.flatten

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


//The flatMapLatest() operator transforms each upstream item into an inner flow,
//but with a powerful twist: whenever a new item arrives from the upstream flow,
//it automatically cancels the previous inner flow and starts the new one.
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