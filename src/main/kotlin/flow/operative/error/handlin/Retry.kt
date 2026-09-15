package flow.operative.error.handlin

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

//runBlockingThe retry(retries) operator is used for error handling and resilience.
//If an exception occurs anywhere upstream in the flow, retry intercepts it,
//restarts the upstream flow, and tries again up to the specified number of times.
//If all retry attempts fail, the exception is allowed to propagate downstream.

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