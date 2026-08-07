package flow.operative.error.handlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flow {

        emit(1)
        emit(2)
        emit(3)

    }

        .onCompletion {

            println("Flow Completed")

        }

        .collect(::println)

    println("----------------- Second Example --------")

    flow {

        emit(1)

        throw RuntimeException()

    }

        .catch {

            println("Caught Error")

        }

        .onCompletion {

            println("Completed")

        }

        .collect(::println)

    println("----------------- Third Example --------")
    flow {

        repeat(10){

            emit(it)

            delay(500)

        }

    }
        .onCompletion {

            println("Cancelled or Completed")

        }


        .take(3)

        .collect(::println)

}