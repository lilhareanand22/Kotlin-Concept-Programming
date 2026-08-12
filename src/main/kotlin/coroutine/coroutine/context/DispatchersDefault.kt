package coroutine.coroutine.context

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main thread: ${Thread.currentThread().name}")

    // Runs on CPU-bound thread pool (limited to CPU core count)
    launch(Dispatchers.Default) {
        println("CPU-intensive task started on thread: ${Thread.currentThread().name}")

        // Simulate heavy calculation
        val sum = (1..1_000_000).sum()

        println("CPU task finished. Result: $sum on thread: ${Thread.currentThread().name}")
    }.join()
}