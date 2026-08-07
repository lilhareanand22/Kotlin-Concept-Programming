package flow.operative.error.handlin

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flow {

        emit(1)

        emit(2)

        throw RuntimeException("Network Error")

        emit(3)

    }

        .catch {

            println("Caught : ${it.message}")

        }

        .collect(::println)

}