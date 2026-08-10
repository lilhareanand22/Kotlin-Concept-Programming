package flow.operative.terminal

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val result = flowOf(10, 20, 30)
        .last()

    println(result)
}