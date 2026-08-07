package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(10,20,30,40,50)
        .drop(2)
        .collect(::println)

    println("----------------- Second Example --------")
    val flow = flow {

        for(i in 1..5){

            println("Producing $i")

            emit(i)

        }

    }

    flow

        .drop(2)

        .collect {

            println("Collected $it")

        }


}