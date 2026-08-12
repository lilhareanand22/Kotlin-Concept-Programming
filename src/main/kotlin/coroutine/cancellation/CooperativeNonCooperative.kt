package coroutine.cancellation

import kotlinx.coroutines.*

fun main() = runBlocking {

    // Example: Non-Cooperative (This will keep running even after cancellation!)
    val job1 = launch(Dispatchers.Default) {
        var i = 0
        while (i < 5) {
            // No suspending function here!
            // Thread.sleep(100) // Don't use this, it blocks!
            println("Non-cooperative loop: $i")
            i++
        }
    }

    delay(10)
    job1.cancel()
    println("Job1 cancelled, but did it stop?")

    // Example: Cooperative (Using yield() or isActive)
    val job2 = launch(Dispatchers.Default) {
        var i = 0
        while (i < 5 && isActive) { // Check isActive manually
            println("Cooperative loop: $i")
            yield() // Or use delay(10) - suspension points check for cancellation
            i++
        }
    }

    delay(10)
    job2.cancel()
    println("Job2 cancelled and stopped.")
}