package coroutine.concurrency.propagation

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main(): Unit = runBlocking {
    println("=== Standard Coroutine Scope (Cascading Failure) ===")
    try {
        coroutineScope { // Standard scope: if one fails, everything cancels
            launch {
                println("Task 1 started")
                delay(500)
                println("Task 1 failed!")
                throw ArithmeticException("Boom!")
            }

            launch {
                println("Task 2 started")
                delay(1000)
                println("Task 2 completed successfully") // This will NEVER run because Task 1 failed and cancelled the scope
            }
        }
    } catch (e: Exception) {
        println("Caught root exception: ${e.message}\n")
    }
}
