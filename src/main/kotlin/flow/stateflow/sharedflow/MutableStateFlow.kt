package flow.stateflow.sharedflow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    val state = MutableStateFlow(0)

    launch {

        state.collect {

            println("Collector : $it")

        }

    }

    delay(100)

    state.value = 1

    delay(100)

    state.value = 2

    delay(100)

    state.value = 3





}