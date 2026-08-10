package generic





fun main(){
    val result = calculate(10, 5) { x, y ->
        x+y

    }
    println(result)
}
fun calculate(a : Int, b:Int, operation:(Int, Int) -> Int) : Int {
    return operation(a,b)
}