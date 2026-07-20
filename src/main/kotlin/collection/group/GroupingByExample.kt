package collection.group

fun main() {
    val namelist = listOf(

        "Anand",

        "Amit",

        "Rahul",

        "Ravi",

        "Ajay"

    )
    // Count how many elements belong to each group
    val countName = namelist.groupingBy {
        it.first()
    }.eachCount()
    println(countName)

// fold example
    val employees = listOf(
        Employee("Anand", "Android", 100000),
        Employee("Rahul", "Backend", 90000),
        Employee("Amit", "Android", 120000),
        Employee("Vijay", "Backend", 110000),
        Employee("Neha", "Android", 130000)
    )

    val totalSalary = employees
        .groupingBy { it.department }
        .fold(0) { total, employee ->
            total + employee.salary
        }

    println("fold $totalSalary")
    // reduce example
    val highestSalary = employees
        .groupingBy { it.department }
        .reduce { key, currentMax, employee ->

            if (currentMax.salary > employee.salary)
                currentMax
            else
                employee
        }

    println(" reduce $highestSalary")

    //aggregate
    val names = employees
        .groupingBy { it.department }
        .aggregate { _, employeename: String?, employee, first ->

            if (first)
                employee.name
            else
                employeename + ", " + employee.name
        }

    println("aggregate $$names")

}

data class Employee(
    val name: String,
    val department: String,
    val salary: Int
)