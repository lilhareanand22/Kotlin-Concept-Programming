package flow.operative.error.handlin

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

var attempt = 0

fun main() = runBlocking {

    flow {

        attempt++

        println("Attempt : $attempt")

        if (attempt < 3) {
            throw RuntimeException("Network Error")
        }

        emit("Success")

    }

        .retry(2)

        .collect(::println)
}