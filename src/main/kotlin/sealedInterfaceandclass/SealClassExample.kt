package sealedInterfaceandclass


sealed class ApiResult {
    object Loading: ApiResult()
    data class Success(val data: String) : ApiResult()
    data class Error(val message:String) :  ApiResult()
}

fun handleResponse(state: ApiResult) {
    when (state) {
        is ApiResult.Loading -> {
            println("Loading...")
        }

        is ApiResult.Success -> {
            println("Data: ${state.data}")
        }

        is ApiResult.Error -> {
            println("Error: ${state.message}")
        }
    }
}

fun main() {
    handleResponse(ApiResult.Loading)
}