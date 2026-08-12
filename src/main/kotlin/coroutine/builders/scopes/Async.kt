package coroutine.builders.scopes

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main start. Fetching data concurrently...")

    val totalTime = measureTimeMillis {
        // Start two async tasks concurrently
        val deferred1 = async { fetchFromNetwork("API-1", 1000L) }
        val deferred2 = async { fetchFromNetwork("API-2", 1000L) }

        // Await both results
        val result1 = deferred1.await()
        val result2 = deferred2.await()

        println("Got results: [$result1] and [$result2]")
    }

    // Total time will be ~1000ms, not 2000ms, because they ran concurrently!
    println("Total execution time: $totalTime ms")
}

suspend fun fetchFromNetwork(apiName: String, delayTime: Long): String {
    delay(delayTime) // Simulate network latency
    return "Data from $apiName"
}