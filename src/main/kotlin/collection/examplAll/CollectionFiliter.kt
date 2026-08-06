package collection.examplAll




fun main() {
    val students = StudentRepository.getStudents()
        .flatMap{it.subscribedCourses}
        .filter{it.paid}
        .groupingBy{it}
        .eachCount()
        .entries
        .sortedByDescending{it.value}
        .take(10)


    println(students)

}