package coroutine.coroutine.context

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main thread: ${Thread.currentThread().name}")

    // Runs on a shared thread pool designed for blocking I/O
    launch(Dispatchers.IO) {
        println("IO task started on thread: ${Thread.currentThread().name}")
        delay(500L) // Simulate network/disk wait
        println("IO task finished on thread: ${Thread.currentThread().name}")
    }.join()
}