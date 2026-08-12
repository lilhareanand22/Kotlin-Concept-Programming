package coroutine.coroutine.context

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("=== 1. USING withContext (Sequential & Returns Result) ===" +
            "\nThread: ${Thread.currentThread().name}")

    // withContext switches context, computes value, and returns it directly in a clean, sequential manner
    val resultFromIO = withContext(Dispatchers.IO) {
        println("withContext running on: ${Thread.currentThread().name}")
        delay(500L) // Simulate network call
        "Data fetched from Network" // Return value
    }

    println("Back on main thread. Result: '$resultFromIO'")
    println("Thread: ${Thread.currentThread().name}\n")


    println("=== 2. USING launch (Concurrent / Fire-and-Forget) ===" +
            "\nThread: ${Thread.currentThread().name}")

    var resultFromLaunch = ""

    // launch creates a brand new coroutine. It doesn't return a value directly.
    val myJob = launch(Dispatchers.IO) {
        println("launch running on: ${Thread.currentThread().name}")
        delay(500L)
        resultFromLaunch = "Data fetched via launch"
    }

    // Because launch is asynchronous, we must explicitly wait for it to finish if we need the result
    myJob.join()

    println("Back on main thread. Result: '$resultFromLaunch'")
    println("Thread: ${Thread.currentThread().name}")
}