package flow.operative.allcombine

import kotlinx.coroutines.flow.*
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

    println("----------------- Second Example --------")
    flowOf(1, 2, 3, 4, 5)
        .filter { it % 2 == 0 }      // Keep even numbers
        .map { it * 10 }             // Transform
        .onEach { println("After map: $it") } // Observe
        .collect { println("Final: $it") }

}