package scop.function


// LET - Object reference - it, Return Value - Lambda result, Is extension function - Yes
// Executing a lambda on non-nullable objects


//let
//The context object is available as an argument (it).
//
//The return value is the lambda result.
fun main() {
    println("without let")
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    val resultList = numbers.map { it.length }.filter { it > 3 }
    println(resultList)

    println("with let")
    val numbers1 = mutableListOf("one", "two", "three", "four", "five")
    numbers1.map { it.length }.filter { it > 3 }.let {
        println("use of let")
        println(it)
        // and more function calls if needed
    }

    val str: String? = "Hello"
//processNonNullString(str)       // compilation error: str can be null
    val length = str?.let {
        println("let() called on $it")
       // processNonNullString(it)      // OK: 'it' is not null inside '?.let { }'
       println(it.length)
    }
    val modifiedFirstItem = numbers.first().let { firstItem ->
        println("The first item of the list is '$firstItem'")
        if (firstItem.length >= 5) firstItem else "!" + firstItem + "!"
    }.uppercase()
    println("First item after modifications: '$modifiedFirstItem'")

}