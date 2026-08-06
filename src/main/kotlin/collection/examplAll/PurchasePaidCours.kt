package collection.examplAll

/*Problem Statement

A learning platform has many students.

Each student subscribes to multiple courses.

A course has:

name
paid (true/false)

Find the Top 5 Most Purchased Paid Courses.
*/


fun main() {
    val students = StudentRepository.getStudents()
        .flatMap { it.subscribedCourses}
        .filter {it.paid}
        .groupingBy {it}
        .eachCount()
        .entries
        .sortedByDescending {it.value}
        .take(10)
        .associateBy{it.key to it.value}


//    val result = StudentRepository.getStudents()
//        .flatMap { it.subscribedCourses }
//        .filter { it.paid }
//        .groupBy { it.name }
//        .mapValues { (_, courses) ->
//            courses.size
//        }
//        .entries
//        .sortedByDescending { it.value }
//        .take(5)
//        .associate { it.toPair() }



    println(students)
}
