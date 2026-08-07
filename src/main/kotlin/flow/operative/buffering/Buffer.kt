package flow.operative.buffering

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    flow {
        for(i in 1..3){

            delay(100)

            println("Produced $i")

            emit(i)

        }

    }

        .buffer()

        .collect {

            delay(1000)

            println("Collected $it")

        }


}