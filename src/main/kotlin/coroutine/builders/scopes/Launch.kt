package coroutine.builders.scopes

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main start on thread: ${Thread.currentThread().name}")

    // launch starts a new coroutine without blocking and returns a Job
    val job = launch {
        println("Launch coroutine started on thread: ${Thread.currentThread().name}")
        delay(1000L) // Simulate background work
        println("Launch coroutine finished.")
    }

    println("Doing other work while launch is running...")

    // Wait for the launched coroutine to finish before main exits
    job.join()

    println("Main end.")
}