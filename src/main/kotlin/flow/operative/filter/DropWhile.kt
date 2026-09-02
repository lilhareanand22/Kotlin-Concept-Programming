package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

//By applying this operator, you tell the flow: "Keep throwing away every log message as long as it isn't 'SYSTEM_READY'.
//The exact moment you see 'SYSTEM_READY', stop dropping, keep that message, and stream everything that comes after it.




fun main() = runBlocking {

    flowOf(10,20,30,60,5,70)

        .dropWhile {

            it < 50

        }

        .collect(::println)

}