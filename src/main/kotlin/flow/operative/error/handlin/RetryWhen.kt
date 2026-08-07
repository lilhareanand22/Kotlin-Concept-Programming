package flow.operative.error.handlin

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    var count = 0

//    flow {
//
//        count++
//
//        println("Attempt : $count")
//
//        throw RuntimeException("Network Error")
//
//    }
//
//        .retryWhen { cause, attempt ->
//
//            println("Retry : ${attempt + 1} after: ${cause.message}")
//
//            attempt < 2
//
//        }
//
//        .catch { cause ->
//
//            println("Failed: ${cause.message}")
//
//        }
//
//        .collect()

}
