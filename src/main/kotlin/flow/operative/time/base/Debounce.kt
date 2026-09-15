package flow.operative.time.base

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


//By applying .debounce(300) (300 milliseconds), you tell the app:
//"Wait and see if the user stops typing. If they keep typing faster than every 300ms,
//ignore the intermediate queries. Only fire the search request when the user pauses for at least 300ms."
fun main() = runBlocking {

    flow {

        emit("A")

        delay(100)

        emit("An")

        delay(100)

        emit("Ana")

        delay(100)

        emit("Anand")

    }

        .debounce(300)

        .collect(::println)

    println("----------------- Second Example --------")
    flow {

        emit(1)

        delay(100)

        emit(2)

        delay(100)

        emit(3)

        delay(600)

        emit(4)

    }

        .debounce(300)

        .collect(::println)

}