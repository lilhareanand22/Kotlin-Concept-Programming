package dataAndenum


internal data class Employee(val name: String, val experience : Int)
 data class Bill(val original:Double, val final:Double)


fun main() {
    val employee = Employee("Anand", 12)
    println(employee.name)

    // Kotlin destructuring
    val (name, price) = Pair("iPhone",900.0)
    println(name)
    println(price)
    val bill = calculateBill(1000.0, 10.0)
    println(bill.original)
    println(bill.final)
}
fun calculateBill(price:Double, discountPercentage:Double) : Bill {
    val finalPrice = price * ( 1 - discountPercentage / 100)
    return Bill(price, finalPrice)
}