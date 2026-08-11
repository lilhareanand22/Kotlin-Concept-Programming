package coroutine.builders_scopes

import kotlinx.coroutines.*

fun main() {
    println("Program starts on thread: ${Thread.currentThread().name}")

    // runBlocking blocks the current thread until the coroutine inside completes
    runBlocking {
        println("Inside runBlocking on thread: ${Thread.currentThread().name}")
        delay(1000L) // Non-blocking delay
        println("Task inside runBlocking completed.")
    }

    println("Program ends.")
}