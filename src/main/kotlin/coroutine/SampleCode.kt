package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun main() = runBlocking {
    val time = measureTimeMillis {
        // Launching 100,000 coroutines concurrently
        val jobs = List(100_000) {
            launch {
                delay(1000L) // Simulate a non-blocking delay (like a network call)
            }
        }
        jobs.joinAll() // Wait for all coroutines to finish
    }
    println("Took $time ms to run 100,000 coroutines!")
}