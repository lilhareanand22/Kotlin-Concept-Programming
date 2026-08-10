package flow.operative.terminal

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    flow {
        emit(1)
        delay(100)
        emit(2)
    }
        .collectLatest { value ->

            println("Start $value")

            delay(500)

            println("End $value")
        }
}