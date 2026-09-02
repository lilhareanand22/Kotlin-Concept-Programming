package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

//By applying .takeWhile { it < 100 }, you tell the flow: "Keep emitting progress updates as
//long as the percentage is under 100%. The exact moment we hit or pass 100%, stop the flow."

fun main() = runBlocking {

    flowOf(10, 20, 30, 40,60,5, 70)
        .takeWhile { it < 50 }
        .collect(::println)

}