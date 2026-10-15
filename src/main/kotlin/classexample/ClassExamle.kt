package classexample



class Employee(var name: String, var salary:Double) {
    fun displayName() {
        print("Name: $name, Salary: $salary")
    }
}

fun main() {
    val employee = Employee("Anand", 30000.0)
    employee.displayName()
}