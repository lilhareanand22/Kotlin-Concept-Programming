package flow.operative.allcombine

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    flowOf(1,2,3,4,5,6)

        .filter {
            it % 2 == 0
        }

        .map {
            it * 10
        }

        .collect(::println)

}