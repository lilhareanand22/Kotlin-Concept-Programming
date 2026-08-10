package flow.stateflow.sharedflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    val events = MutableSharedFlow<String>()

    launch {

        events.collect {

            println("Collector : $it")

        }

    }

    delay(100)

    events.emit("Hello")

    delay(100)

    events.emit("World")

}