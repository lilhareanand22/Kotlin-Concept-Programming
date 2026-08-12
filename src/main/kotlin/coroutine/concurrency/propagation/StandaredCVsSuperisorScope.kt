package coroutine.concurrency.propagation

import kotlinx.coroutines.*

fun main() = runBlocking {
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

    println("=== SupervisorScope (Isolated Failures) ===")
    supervisorScope { // Supervisor scope: children failures are isolated
        val child1 = launch {
            println("Task A started")
            delay(500)
            println("Task A failed!")
            throw ArithmeticException("Boom A!")
        }

        val child2 = launch {
            println("Task B started")
            delay(1000)
            println("Task B completed successfully!") // This WILL run successfully despite Task A failing!
        }

        // Wait for them to finish
        joinAll(child1, child2)
    }

    println("Main program finished safely.")
}