package coroutine.mutext

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// A counter incremented by 100,000 coroutines concurrently
suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100 // number of coroutines
    val k = 1000 // number of times each coroutine increments
    val time = kotlin.system.measureTimeMillis {
        coroutineScope {
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

var counter = 0

fun main() = runBlocking {
    massiveRun {
        counter++ // Race condition! Not thread-safe!
    }
    println("Counter = $counter (Expected 100,000)")
}