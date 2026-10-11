package sealedInterfaceandclass

sealed interface Result

    data class Success(
        val data: String
    ) : Result

    data class Error(
        val message: String
    ) : Result

    object Loading : Result


fun handleResult(result: Result) {

    when (result) {

        Loading -> {
            println("Loading")
        }

        is Success -> {
            println(result.data)
        }

        is Error -> {
            println(result.message)
        }
    }
}

fun main() {
    handleResult(Success("Data loaded"))
}