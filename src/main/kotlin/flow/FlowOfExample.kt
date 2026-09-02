package flow

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    val number = flowOf(1,2,3,4,5,6,7)
    number.collect {
        println("Collect number $it")
    }


}