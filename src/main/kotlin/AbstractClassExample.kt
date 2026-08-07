abstract class Animal1 {

    abstract fun sound()

    fun eat() {
        println("Eating")
    }
}

class Dog1 : Animal1() {

    override fun sound() {
        println("Bark")
    }
}

fun main() {
    val dog = Dog1()
    dog.sound()
    dog.eat()
}