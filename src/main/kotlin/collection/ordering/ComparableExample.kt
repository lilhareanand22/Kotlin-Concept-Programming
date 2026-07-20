package collection.ordering

//Comparable = The object decides how it should be ordered.

data class Employee(
    val name: String,
    val salary: Int
) : Comparable<Employee> {

    override fun compareTo(other: Employee): Int {
        return this.salary.compareTo(other.salary)
    }
}

fun main() {
    val employees = listOf(
        Employee("Vijay", 120000),
        Employee("Anand", 100000),
        Employee("Rahul", 80000)

    )
    println(employees.sorted())
}