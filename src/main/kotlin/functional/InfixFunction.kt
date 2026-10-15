package functional



infix fun Int.add(value: Int): Int {
    return this + value
}

fun main() {
    val result = 10 add 20

    println(result) // 30
}