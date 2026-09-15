package flow.operative.time.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//By applying .sample(100) (e.g., every 100 milliseconds), you tell the flow:
//"Don't overwhelm the UI with every tick. Just take a snapshot of the latest speed value once every 100ms
//and pass that single value downstream."

fun main() = runBlocking {

    flow {

        emit(1)

        delay(100)

        emit(2)

        delay(100)

        emit(3)

        delay(100)

        emit(4)

        delay(100)

        emit(5)

    }

        .sample(250)

        .collect(::println)

}