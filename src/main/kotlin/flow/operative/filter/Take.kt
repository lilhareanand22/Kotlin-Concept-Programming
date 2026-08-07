package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(10, 20, 30, 40, 50)

        .take(3)

        .collect(::println)

    val flow = flow {

        for (i in 1..10) {

            println("Producing $i")

            emit(i)
        }
    }
    println("----------------- Second Example --------")
    flow
        .take(3)
        .collect {

            println("Collected $it")
        }
}