package flow.operative.terminal

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
//If you use a regular collect, your app will trigger a database write operation
//for "Buy milk". If that write takes time, and the user updates the text again to
//"Buy milk and eggs", the app will finish writing the old text right after the new text,
//potentially causing a race condition where stale data overwrites fresh user data.
fun main() = runBlocking {

    flow {

        for (i in 1..5) {

            delay(100)

            println("Produced $i")

            emit(i)

        }

    }

        .collectLatest {

            println("Start Processing $it")

            delay(300)

            println("Finished $it")

        }

}