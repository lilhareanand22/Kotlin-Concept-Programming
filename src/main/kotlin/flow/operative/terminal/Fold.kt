package flow.operative.terminal

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val result = flowOf(1,2,3,4)
        .fold(10) {accumulator, value ->
            accumulator + value
        }
    println(result)
}


