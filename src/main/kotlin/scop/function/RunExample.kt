package scop.function


// The context object is available as a receiver (this).
// The return value is the lambda result.

class MultiportService(
    var url: String,
    var port: Int
) {
    fun prepareRequest(): String = "Request to $url"

    fun query(request: String): Boolean {
        println(request)
        return true
    }
}


fun main() {
    val service = MultiportService("https://example.kotlinlang.org", 80)
    val result = service.run {
        port = 8080
        query(prepareRequest() + " to port $port")
    }

    println("Request successful: $result")
}
