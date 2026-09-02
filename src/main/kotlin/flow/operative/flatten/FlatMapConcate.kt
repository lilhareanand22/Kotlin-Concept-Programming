package flow.operative.flatten

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


//By applying flatMapConcat, you tell the flow: "Take each photo file name as it arrives,
//trigger its upload flow, but ensure they run strictly one after another
//in a FIFO (First-In, First-Out) queue."
fun main() = runBlocking {


     flowOf(1,2,3)
         .flatMapConcat{ number ->
            flow {
                emit(number)
                emit(number*10)
            }

         }.collect(::println)

    println("----------------- Second Example --------")
    flowOf(1,2)

        .flatMapConcat {

            flow {

                emit(it)

                delay(1000)

                emit(it*10)

            }

        }

        .collect(::println)
}