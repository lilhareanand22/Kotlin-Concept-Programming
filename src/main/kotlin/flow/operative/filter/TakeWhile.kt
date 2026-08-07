package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    flowOf(10, 20, 30, 40,60,5, 70)
        .takeWhile { it < 50 }
        .collect(::println)

}