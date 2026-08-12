package coroutine.mutext

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

val mutex = Mutex()
var counter1 = 0

fun main() = runBlocking {
    val time = kotlin.system.measureTimeMillis {
        coroutineScope {
            repeat(100) {
                launch(Dispatchers.Default) {
                    repeat(1000) {
                        // Protect the critical section using Mutex
                        mutex.withLock {
                            counter1++
                        }
                    }
                }
            }
        }
    }
    println("Counter = $counter1 (Always accurately 100,000!) in $time ms")
}