package flow.operative.buffering

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
//By applying .conflate(), you tell the flow:
//"If the UI is busy processing an older price update,
//skip any intermediate price changes that happen in the meantime, and
//feed the UI only the freshest, most recent price the moment it's ready for the next frame
fun main() = runBlocking {

    flow {

        for (i in 1..5) {

            delay(100)

            println("Produced $i")

            emit(i)
        }

    }

        .conflate()

        .collect {

            delay(300)

            println("Collected $it")

        }

}