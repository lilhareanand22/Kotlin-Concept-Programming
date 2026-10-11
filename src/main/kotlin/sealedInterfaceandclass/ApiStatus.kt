package sealedInterfaceandclass

// 1. Define API status using Sealed Class
sealed class ApiStatus {
    object Loading : ApiStatus()

    data class Success(val data: String) : ApiStatus()

    data class Failure(val message: String) : ApiStatus()
}

// 2. Function returning Pair (statusCode, status)
fun getApiResponse(): Pair<Int, ApiStatus> {
    return Pair(
        200,
        ApiStatus.Success(data = "User loaded")
    )
}

fun main() {

    // 3. Get API response
    val response = getApiResponse()

    println("Status code: ${response.first}")

    // 4. Handle API status
    when (val status = response.second) {

        is ApiStatus.Loading -> {
            println("Loading...")
        }

        is ApiStatus.Success -> {
            println(status.data)
        }

        is ApiStatus.Failure -> {
            println(status.message)
        }
    }
}