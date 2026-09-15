package flow.operative.buffering

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


//The buffer() operator is used to handle speed mismatches between a fast producer and a slow consumer in a Flow.

//By default, Kotlin Flows are sequential and suspending: if the collector takes time to process an item, the emitter is forced to wait. Adding buffer() runs the upstream producer and downstream collector in separate coroutines, using a temporary holding area (a buffer) so the fast producer doesn't have to stall.



// Use case
//By applying .buffer(), you tell the flow: "Let the GPS sensor keep pumping out locations at full speed.
//Store the extra items in a temporary buffer queue so the database
//writer can catch up at its own pace without blocking the producer."
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