package collection.examplAll

data class Student(
    val id: Int,
    val name: String,
    val subscribedCourses: List<Course>
)

data class Course(
    val name: String,
    val paid: Boolean
)

/** Shared, read-only source of students for collection examples. */
object StudentRepository {
    private val kotlin = Course( "Kotlin", true)
    private val compose = Course("Compose", true)
    private val java = Course( "Java", true)
    private val KMP = Course( "kmp", true)
    private val flutter = Course( "Flutter", true)
    private val reactNative = Course("React-Native", true)
    private val Angular = Course("Angular", false)

    private val students: List<Student> = listOf(
        Student(1, "Anand", listOf(kotlin, compose)),
        Student(2, "Rahul", listOf(kotlin, java)),
        Student(3, "Vijay", listOf(compose, kotlin)),
        Student(5, "Akash", listOf(java, KMP)),
        Student(6, "Ramesh", listOf(Angular, flutter)),
        Student(7, "Ganesh", listOf(java, reactNative)),

    )

    fun getStudents(): List<Student> = students
}
