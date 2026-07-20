package collection.group

fun main() {
    val names = listOf(
        "Anand",
        "Amit",
        "Rahul",
        "Ravi",
        "Ajay"
    )

    val result = names.groupBy {
        it.first()
    }
    println(result)

    val students = listOf(
        Student("Anand","Android"),
        Student("Rahul","Backend"),
        Student("Amit","Android"),
        Student("Vijay","Backend")
    )
    val grouped = students.groupBy(

        keySelector = { it.department },

        valueTransform = { it.name }

    )
    println(grouped)
}

data class Student(
    val name: String,
    val department: String
)