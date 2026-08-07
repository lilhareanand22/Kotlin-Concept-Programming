package flow.operative.buffering

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    flow {

        for (i in 1..5) {

            delay(100)

            println("Produced $i")

            emit(i)

        }

    }

        .collectLatest {

            println("Start Processing $it")

            delay(300)

            println("Finished $it")

        }

}