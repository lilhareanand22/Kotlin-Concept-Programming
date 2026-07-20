package collection.ordering

//Comparator
//
//Now suppose tomorrow your manager says:
//
//"Sort employees by name."
//
//Next day:
//
//"Sort by experience."
//
//Next day:
//
//"Sort by salary descending."
//
//Do you change compareTo() every time?
//
//No.
//
//Instead, create a Comparator.

data class EmployeeA(
    val name: String,
    val salary: Int
)

fun main() {
    val employees = listOf(
        Employee("Vijay", 120000),
        Employee("Anand", 100000),
        Employee("Rahul", 80000)

    )
    val sorted = employees.sortedWith(
        compareBy<Employee> { it.name }
    )
    println(sorted)

    val sortedStrings = listOf("aaa", "bb", "c", "b", "a", "aa", "ccc")
        .sortedWith(compareBy<String> { it.length }.thenBy { it })

    println(sortedStrings)
}