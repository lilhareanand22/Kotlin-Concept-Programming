package coroutine.error.handling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

fun main(): Unit = runBlocking {

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Custom Error handler: $exception")
    }
    val scope = CoroutineScope(exceptionHandler + Dispatchers.Default + SupervisorJob())

    scope.launch {
        crashyFunction("Oops! caught by the custom handler")
    }
    delay(100.milliseconds)

    launch {
        crashyFunction("Oops! caught by the default handler")
    }
}

fun crashyFunction(message: String) {
    throw UnsupportedOperationException(message)
}