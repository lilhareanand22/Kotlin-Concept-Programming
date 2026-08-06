package opps

open class Animal {

    open fun sound() {
        println("Animal")
    }
}

class Dog : Animal() {

    override fun sound() {
        println("Dog")
    }
}


fun main() {
    val animal: Animal = Animal()

    animal.sound()
}