package flow.operative.lifecycle.operators

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(1, 2, 3)

        .onEach {
            println("Received : $it")
        }

        .collect {
            println("Collector : $it")
        }
}