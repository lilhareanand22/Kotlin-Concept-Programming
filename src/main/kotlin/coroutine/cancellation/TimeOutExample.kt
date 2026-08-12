package coroutine.cancellation


import kotlinx.coroutines.*

fun main() = runBlocking {
    println("--- 1. withTimeout Example ---")
    try {
        // This task takes 2 seconds, but we set a timeout of 1 second
        withTimeout(1000L) {
            println("Heavy task starting...")
            delay(2000L) // Simulating slow work
            println("Heavy task completed!") // This line will never be reached
        }
    } catch (e: TimeoutCancellationException) {
        println("Caught exception: Task timed out! (${e.message})")
    }

    println("\n--- 2. withTimeoutOrNull Example ---")
    // This returns null instead of throwing an exception if it takes too long
    val result: String? = withTimeoutOrNull(1000L) {
        println("Quick network request starting...")
        delay(2000L) // Completes within the 1-second timeout
        "Success: Data fetched"
    }

    if (result != null) {
        println("Result received: $result")
    } else {
        println("Request timed out and returned null.")
    }
}