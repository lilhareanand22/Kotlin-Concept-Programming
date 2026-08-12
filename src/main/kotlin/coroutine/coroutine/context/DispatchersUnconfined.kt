package coroutine.coroutine.context

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Main thread start: ${Thread.currentThread().name}")

    launch(Dispatchers.Unconfined) {
        // Starts immediately on the thread that launched it (Main thread)
        println("Unconfined starts on: ${Thread.currentThread().name}")

        delay(500L) // Suspension point!

        // After delay, it resumes on whichever thread the delay coroutine finished on (often an IO/timer thread)
        println("Unconfined resumes on: ${Thread.currentThread().name}")
    }.join()
}