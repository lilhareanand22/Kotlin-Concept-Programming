package flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val numberFlow = flow {
        println("Flow Started")

        emit(1)
        emit(2)
        emit(3)

        println("Flow Completed")
    }

    println("Before Collect")

    numberFlow.collect {
        println("Collector received : $it")
    }

    println("After Collect")

    val programmingLanguage = flow {
        emit("Kotlin")
        emit("java")
        emit("Python")
    }

    programmingLanguage.collect {
        println("Collecting the language: $it")
    }
}