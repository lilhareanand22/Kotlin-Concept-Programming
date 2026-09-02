package flow.operative.filter

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


//By applying .distinctUntilChanged(), you tell the flow: "Only pass this emission through to the UI/ViewModel
//if it is different from the last one." If the state is CONNECTED and another CONNECTED arrives right after it,
//the second one is dropped.
fun main() = runBlocking {
    flowOf(
        1,
        1,
        2,
        2,
        3,
        3,
        3,
        4,
        4,
        5
    )

        .distinctUntilChanged()

        .collect(::println)

    println("----------------- Second Example --------")

    flowOf(

        User(1,"John"),

        User(1,"John"),

        User(2,"Alex"),

        User(2,"Alex")

    )

        .distinctUntilChanged()
        .collect(::println)
}


data class User(

    val id:Int,

    val name:String

)