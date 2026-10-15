package function.variables




inline fun execute(action: () -> Unit) {
    action()
}

fun main() {
    execute {
        println("Hello Kotlin")
    }
}

inline fun execute(
    crossinline action: () -> Unit,
    noinline callback: () -> Unit
) {
    action()

    val storedCallback = callback

    storedCallback()
}

inline fun execute1(crossinline action: () -> Unit) {

    val runnable = Runnable {
        action()
    }

    runnable.run()
}