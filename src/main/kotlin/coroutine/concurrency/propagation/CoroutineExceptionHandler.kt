package coroutine.concurrency.propagation

import kotlinx.coroutines.*

fun main() = runBlocking {
    // 1. Define the Exception Handler
    val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("Caught exception in CoroutineExceptionHandler: ${throwable.message}")
        println("Failing context: $context")
    }

    // 2. Create a scope and pass the handler to its context
    // Note: CEH only works on ROOT coroutines (launched directly from a CoroutineScope)
    val scope = CoroutineScope(Dispatchers.Default + exceptionHandler)

    println("--- Example 1: Root launch with CEH ---")
    val job = scope.launch {
        println("Root coroutine starting...")
        throw NullPointerException("Something went null!")
    }

    // Wait for the coroutine to finish and trigger the handler
    job.join()

    // Give it a small break to print before exiting
    delay(100)
    println("\n--- Program finished safely ---")
}