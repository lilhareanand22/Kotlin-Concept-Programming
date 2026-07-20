package collection.transform

private data class FullName(val firstName: String, val lastName: String)

private fun parseFullName(fullName: String): FullName {
    val (firstName, lastName) = fullName.split(" ", limit = 2)
    return FullName(firstName, lastName)
}

fun main() {
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.associateWith { it.length })


    println(numbers.associateBy { it.first().uppercaseChar() })
    println(numbers.associateBy(keySelector = { it.first().uppercaseChar() }, valueTransform = { it.length }))

    val names = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    println(names.associate { name -> parseFullName(name).let { it.lastName to it.firstName } })

}
