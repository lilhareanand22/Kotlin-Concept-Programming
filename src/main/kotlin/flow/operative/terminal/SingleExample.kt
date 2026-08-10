package flow.operative.terminal

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val result = flowOf(100)
        .single()

    println(result)
}