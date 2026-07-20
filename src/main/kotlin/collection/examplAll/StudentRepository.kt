package collection.examplAll

data class Student(
    val id: Int,
    val name: String,
    val subscribedCourses: List<Course>
)

data class Course(
    val id: Int,
    val name: String,
    val paid: Boolean
)

/** Shared, read-only source of students for collection examples. */
object StudentRepository {
    private val kotlin = Course(1, "Kotlin", true)
    private val compose = Course(2, "Compose", true)
    private val java = Course(3, "Java", true)
    private val KMP = Course(5, "kmp", true)
    private val flutter = Course(6, "Flutter", true)
    private val reactNative = Course(7, "React-Native", true)
    private val Angular = Course(8, "Angular", false)

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
