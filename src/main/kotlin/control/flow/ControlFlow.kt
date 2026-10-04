package control.flow

fun main() {
    val age = 18
    if(age >= 18) {
        println("Adult")
    } else {
        println("Minor")
    }

    val score = 85
    when(score) {
        in 90..100 -> println("Grade A")
        in 80..89 -> print("Grade B")
        else -> print("Grade C")
    }

    // fol loop
    for (number in 0..5){
        println(number)
    }

    //while loop
    var count = 0
    while(count <= 3) {
        println(count)
        count++
    }
}