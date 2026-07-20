package collection.transform

fun main() {
    val names = listOf(
            "Anand",
            "Rahul",
            "Vijay"
        )
    val ages = listOf(
            30,
            28,
            35
        )

    val result = names.zip(ages)
    println(result)

    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")
    println(colors zip animals)

    val twoAnimals = listOf("fox", "bear")
    println(colors.zip(twoAnimals))

    val numberPairs = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
    println(numberPairs.unzip())

}