package coroutine.coroutine.context



import kotlinx.coroutines.*

fun main() = runBlocking {
    println("1. [Thread: ${Thread.currentThread().name}] Starting workflow...")

    // Step 2: Switch to IO to fetch raw data
    val rawData = withContext(Dispatchers.IO) {
        println("2. [Thread: ${Thread.currentThread().name}] Fetching raw data from network/disk...")
        delay(500L) // Simulate network delay
        "RawJsonData[ItemA, ItemB, ItemC]"
    }

    // Step 3: Switch to Default to do heavy CPU-intensive parsing
    val parsedData = withContext(Dispatchers.Default) {
        println("3. [Thread: ${Thread.currentThread().name}] Parsing and processing heavy data...")
        delay(500L) // Simulate heavy CPU computation
        // Transform the raw string
        rawData.replace("RawJsonData", "ParsedObjects")
    }

    // Step 4: Switch back to IO to save the parsed data locally
    withContext(Dispatchers.IO) {
        println("4. [Thread: ${Thread.currentThread().name}] Saving parsed data to local database...")
        delay(300L) // Simulate database write
    }

    // Step 5: Back to original thread (simulating Main UI thread) to wrap up
    println("5. [Thread: ${Thread.currentThread().name}] Workflow complete! Result: $parsedData")
}