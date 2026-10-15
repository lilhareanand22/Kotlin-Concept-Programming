package dataAndenum



data class User(
    val id:Int,
    var name: String,
    var age: Int
) {

    fun displayName() {
        print("Name $name and Age:$age")
    }

}

fun main() {
    val  user = User(1, "Anand", 38)
    user.displayName()
}