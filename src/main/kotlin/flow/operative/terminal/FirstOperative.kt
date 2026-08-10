package flow.operative.terminal

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    val result = flowOf(10, 20, 30)
        .first()

    println(result)
}