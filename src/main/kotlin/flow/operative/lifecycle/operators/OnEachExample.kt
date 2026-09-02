package flow.operative.lifecycle.operators

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
// Use cases
//Logging
//Analytics
//Saving to a database
//Updating metrics
//Debugging
//Progress tracking


fun main() = runBlocking {

    flowOf(1, 2, 3)

        .onEach {
            println("Received : $it")
        }

        .collect {
            println("Collector : $it")
        }
}