package flow.operative.allcombine

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(1,2,3,4,5)

        .drop(2)

        .map {

            println("Mapping $it")

            it * 10

        }

        .collect(::println)


}