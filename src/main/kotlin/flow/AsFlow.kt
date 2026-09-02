package flow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
   val listValue =  listOf(1,3,4,5).asFlow()
    listValue.collect {
        println("Collecting the value  $it")
    }
}