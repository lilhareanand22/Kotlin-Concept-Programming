package flow

import kotlinx.coroutines.delay

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val orderFlow = flow {

        println("Preparing Pizza...")
        delay(2000)
        emit("🍕 Pizza Ready")

        println("Preparing Burger...")
        delay(2000)
        emit("🍔 Burger Ready")

        println("Preparing Juice...")
        delay(2000)
        emit("🥤 Juice Ready")
    }

    println("Customer placed the order.")

    orderFlow.collect {
        println("Customer received -> $it")
    }

    println("Order Completed.")
}