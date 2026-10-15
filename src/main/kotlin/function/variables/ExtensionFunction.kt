package function.variables


fun String.addGreeting(): String {
    return "Hello, $this"
}

fun main() {
    val name = "Anand"
    println(name.addGreeting())
}