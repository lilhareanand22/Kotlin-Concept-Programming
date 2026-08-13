package coroutine.error.handling

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {
    supervisorScope {
        val ticks = async {
            // Child 1 ticks
            var ticks = 0
            repeat(10) {
                println("Tick")
                ticks++
                delay(100.milliseconds)
            }
            ticks
        }
        launch {
            // Child 2 stops the clock
            delay(500.milliseconds)
            // The ticking doesn't stop
            throw StopTheClockException("Child 2 Stop!")
        }

        println("Ticks ${ticks.await()}")
        println("End")
    }
}