package coroutine.concurrency.propagation

import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope


fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Task A failed safely: ${exception.message}")
    }

    supervisorScope {
        val child1 = launch(exceptionHandler) {
            println("Task A started")
            delay(500)
            println("Task A failed!")
            throw ArithmeticException("Boom A!")
        }
        val child2 = launch {
            println("Task B started")
            delay(500)
            println("Task B completed successfully")
        }
        joinAll(child1, child2)
    }
    println("Main program finished safely.")
}
