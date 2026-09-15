package coroutine.builders.scopes

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking { // 1. runBlocking bridges the main thread into the coroutine world
    println("--- 1. runBlocking Example ---")
    println("Main program starts on thread: ${Thread.currentThread().name}")

    // 2. launch Example (Fire-and-Forget)
    println("\n--- 2. launch Example ---")
    val launchJob = launch {
        delay(500L) // Simulate background work
        println("Launch task finished on thread: ${Thread.currentThread().name}")
    }

    // We can wait for the launch job to complete if needed
    launchJob.join()

    // 3. async Example (Concurrent tasks returning results)
    println("\n--- 3. async Example ---")
    val totalTime = measureTimeMillis {
        // Start both async tasks concurrently
        val deferredData1 = async { fetchUserData() }
        val deferredData2 = async { fetchUserSettings() }

        println("Doing other work while fetching data concurrently...")

        // Await the results using .await()
        val data1 = deferredData1.await()
        val data2 = deferredData2.await()

        println("Received results: $data1 and $data2")
    }
    println("Async tasks took a total of: $totalTime ms")

    println("\nMain program ends.")
}

// Helper suspending functions simulating network calls
suspend fun fetchUserData(): String {
    delay(1000L) // Simulate 1 second network delay
    println("Fetched User Data on thread: ${Thread.currentThread().name}")
    return "User: Alice"
}

suspend fun fetchUserSettings(): String {
    delay(1000L) // Simulate 1 second network delay
    println("Fetched User Settings on thread: ${Thread.currentThread().name}")
    return "Settings: DarkMode"
}