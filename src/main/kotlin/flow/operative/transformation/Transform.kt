package flow.operative.transformation

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(1, 2, 3)

        .transform { number ->

            emit("Original : $number")

            emit("Square : ${number * number}")
        }

        .collect {
            println(it)
        }

    println("----------------- Second Example --------")
    flowOf(1,2,3,4,5)
        .transform {
            if(it % 2 == 0){
                emit(it)
            }
        }
        .collect(::println)
    println("----------------- Third Example --------")
    flowOf(1,2,3)
        .transform {
            emit(it * 10)
        }
        .collect(::println)

    println("----------------- Fourth Example --------")
    flowOf(2,4,6)
        .transform{
            emit("Number: $it")
            emit("Double: ${it*2}")
        }.collect{
            println(it)
        }
}