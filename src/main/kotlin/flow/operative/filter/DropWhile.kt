package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(10,20,30,60,5,70)

        .dropWhile {

            it < 50

        }

        .collect(::println)

}