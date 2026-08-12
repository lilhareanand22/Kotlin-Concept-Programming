package coroutine.coroutine.context

import kotlinx.coroutines.*

fun main() = runBlocking {
    // In Android, this switches execution to the main UI thread.
    // (In a pure console JVM app, this throws an IllegalStateException because no Main dispatcher is present).
    try {
        withContext(Dispatchers.Main) {
            println("Running on Main thread: ${Thread.currentThread().name}")
        }
    } catch (e: Exception) {
        println("Dispatchers.Main requires an Android/UI environment in pure JVM: ${e.message}")
    }
}