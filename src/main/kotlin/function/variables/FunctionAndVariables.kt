package function.variables

fun main() {
 println("Adding two number: ${add(10,20)}")
    greet(name = "Anand")

    val operation = ::add

    val result = operation(10, 20)
    println("refernce of method: $result")
}

internal fun add(a:Int, b:Int): Int {
    return a + b
}

//Default Parameter
internal fun greet(
    name: String,
    message: String = "Welcome"
) {
    println("$message, $name")
}