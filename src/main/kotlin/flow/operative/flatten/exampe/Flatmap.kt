package flow.operative.flatten.exampe

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun getDetails(id: Int): Flow<String> = flow {
    emit("Start $id")
    delay(500)
    emit("End $id")
}

fun main() = runBlocking {
    flowOf(1, 2, 3)
        .flatMapConcat { id ->
            getDetails(id)
        }
        .collect { result ->
            println(result)
        }
}