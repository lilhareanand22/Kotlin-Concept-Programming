package coroutine.stateflowexaple



import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {
    println("=== 1. StateFlow Example ===")
    // StateFlow requires an initial value and retains the latest state
    val stateFlow = MutableStateFlow("Initial State")

    // Collector 1 starts listening
    val job1 = launch {
        stateFlow.collect { value ->
            println("Collector 1 received: $value")
        }
    }

    delay(100)
    stateFlow.value = "Loading State"

    delay(100)
    stateFlow.value = "Success State"

    // Late collector gets the *current latest* value immediately
    launch {
        stateFlow.collect { value ->
            println("Late Collector received current state: $value")
        }
    }

    delay(100)
    job1.cancel()


    println("\n=== 2. SharedFlow Example ===")
    // SharedFlow does not have an initial value
    val sharedFlow = MutableSharedFlow<String>(replay = 1) // replay last 1 value for new subscribers

    // Emit an event BEFORE any collector is listening
    sharedFlow.emit("Event 1 (Missed by late collectors unless replayed)")

    // Late collector with replay = 1 will catch "Event 1"
    launch {
        sharedFlow.collect { event ->
            println("SharedFlow Collector received: $event")
        }
    }

    delay(100)
    sharedFlow.emit("Event 2")

    // Clean up
    coroutineContext[Job]?.children?.forEach { it.cancel() }
}