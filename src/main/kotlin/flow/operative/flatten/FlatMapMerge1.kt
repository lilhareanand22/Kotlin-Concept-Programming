package flow.operative.flatten

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking

data class ProductDetails(val id: String, val name: String)

class ProductRepository {

    // Simulates a network call to fetch product details by ID
    fun fetchProductDetails(productId: String): Flow<ProductDetails> = flow {
        delay(400) // Simulating network latency
        emit(ProductDetails(productId, "Item Name for $productId"))
    }
}

class WishlistViewModel(private val repository: ProductRepository) {

    suspend fun loadWishlistDetails() {
        // Upstream flow emitting product IDs
        val wishlistIdsFlow: Flow<String> = flow {
            emit("prod_101")
            emit("prod_102")
            emit("prod_103")
        }

        wishlistIdsFlow
            .flatMapMerge { productId ->
                // Fires network requests for ALL IDs concurrently!
                repository.fetchProductDetails(productId)
            }
            .collect { product ->
                println("Loaded: ${product.name}")
            }
    }
}

fun main() = runBlocking {

    val wishlist = WishlistViewModel(ProductRepository())
    wishlist.loadWishlistDetails()
}