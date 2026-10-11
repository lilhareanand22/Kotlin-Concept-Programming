package dataAndenum




enum class ApiStatus {
    LOADING,
    SUCCESS,
    ERROR
}

fun handleStatue(status: ApiStatus) {
    when(status) {
        ApiStatus.LOADING -> {
            println("Show progress bar")
        }
        ApiStatus.SUCCESS -> {
            println("Show data")
        }
        ApiStatus.ERROR -> {
            println("Show error")
        }
    }
}

enum class HttpStatus(
    val code: Int,
    val description: String
) {
    SUCCESS(200, "Success"),
    NOT_FOUND(404, "Not Found"),
    SERVER_ERROR(500, "Internal Server Error")
}

fun main() {
    handleStatue(ApiStatus.LOADING)
    val status = HttpStatus.NOT_FOUND

    println(status.code)
    println(status.description)

    ApiStatus1.SUCCESS.showMessage()

    println(Operation.ADD.execute(10, 5))
    println(Operation.MULTIPLY.execute(10, 5))

    for (direction in Direction.entries) {
        println(direction)
    }

    val direction = Direction.SOUTH

    println(direction.name)
    println(direction.ordinal)
}

enum class ApiStatus1(val message: String) {

    LOADING("Loading..."),
    SUCCESS("Data loaded"),
    ERROR("Something went wrong");

    fun showMessage() {
        println(message)
    }
}

enum class Operation {

    ADD {
        override fun execute(a: Int, b: Int): Int {
            return a + b
        }
    },

    SUBTRACT {
        override fun execute(a: Int, b: Int): Int {
            return a - b
        }
    },

    MULTIPLY {
        override fun execute(a: Int, b: Int): Int {
            return a * b
        }
    };

    abstract fun execute(a: Int, b: Int): Int
}

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
}