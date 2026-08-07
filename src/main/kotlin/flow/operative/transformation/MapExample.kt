package flow.operative.transformation

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
     val number = flowOf(1,2,3,4,5)
     number.map{ it*10

     }.collect{
         println(it)
     }

    println("----------------- Second Example --------")
    val multi = flowOf(5,10,15,20)
    multi.map {
        it*2
    }.collect {

        println(it)
    }

    println("----------------- Third Example --------")
    val fruit = flowOf(
        "apple",
        "banana",
        "orange"
    )
    fruit.map{
        it.uppercase()
    }.collect {
        println(it)
    }

    println("----------------- Fourth Example --------")

    val employee = flowOf(
        Employee(1, "John", 80),
        Employee(2, "Alex", 90),
        Employee(3, "Sam", 70)
    )

    employee.map {
        it.name
    }.collect {
        println(it)
    }
}


data class Employee(
    val id: Int,
    val name: String,
    val salary: Int
)