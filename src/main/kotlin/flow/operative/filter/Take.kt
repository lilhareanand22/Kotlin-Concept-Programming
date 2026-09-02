package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    // Use case: limit a Flow to the first few values when you only need a
    // fixed number of results, such as a preview, top-N items, or pagination.

    //The Solution: Using .take(3)
   // By applying .take(3) to your flow, you tell the app: "Listen to the incoming article stream, grab the first 3 that arrive, push them to the UI, and then immediately close the connection to save battery and resources."
    flowOf(10, 20, 30, 40, 50)

        .take(3)

        .collect(::println)

    val flow = flow {

        for (i in 1..10) {

            println("Producing $i")

            emit(i)
        }
    }
    println("----------------- Second Example --------")
    flow
        .take(3)
        .collect {

            println("Collected $it")
        }
}
