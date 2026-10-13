package flow.operative.error.handlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    var count = 0

    var attempts = 0

    flow {
        attempts++

        if (attempts < 3) {
            throw RuntimeException("Network error")
        }

        emit("Data loaded")
    }.retryWhen { cause, attempt ->
        println("Retry count: $attempt")

        delay(1000)

        attempt < 2
    }.catch { exception ->
        println("Failed: ${exception.message}")
    }.collect { value ->
        println(value)
    }

}
