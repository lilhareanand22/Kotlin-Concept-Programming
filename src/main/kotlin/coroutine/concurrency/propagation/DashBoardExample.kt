package coroutine.concurrency.propagation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

/** The state that a dashboard can expose to its UI. */
sealed interface DashboardState {
    data class Success(
        val balance: AccountBalance,
        val transactions: List<Transaction>,
        val banners: List<PromotionalBanner>
    ) : DashboardState

    data class Error(val message: String) : DashboardState
}

data class AccountBalance(val amount: Double, val currency: String = "USD")

data class Transaction(
    val id: String,
    val description: String,
    val amount: Double
)

data class PromotionalBanner(val id: String, val message: String)

suspend fun loadDashboardData(): DashboardState = supervisorScope {
    // 1. Critical task: Account Balance
    val balanceDeferred = async(Dispatchers.IO) {
        // A critical failure is reported when this Deferred is awaited.
        fetchAccountBalance()
    }

    // 2. Critical task: Transactions
    val transactionsDeferred = async(Dispatchers.IO) {
        fetchRecentTransactions()
    }

    // 3. Non-critical task: Banners (Wrap in try-catch to swallow non-fatal errors)
    val bannersDeferred = async(Dispatchers.IO) {
        try {
            fetchPromotionalBanners()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emptyList() // Fallback gracefully
        }
    }

    // Await results. If balance or transactions throw, .await() rethrows the exception
    // and the screen receives an error state. The supervisor keeps sibling work isolated.
    try {
        DashboardState.Success(
            balance = balanceDeferred.await(),
            transactions = transactionsDeferred.await(),
            banners = bannersDeferred.await()
        )
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        DashboardState.Error(e.message ?: "Failed to load critical data")
    }
}

// These functions stand in for repository or network calls in this example.
private suspend fun fetchAccountBalance(): AccountBalance {
    delay(100)
    return AccountBalance(amount = 2_450.75)
}

private suspend fun fetchRecentTransactions(): List<Transaction> {
    delay(150)
    return listOf(
        Transaction("txn-001", "Coffee shop", -4.50),
        Transaction("txn-002", "Salary", 3_200.00)
    )
}

private suspend fun fetchPromotionalBanners(): List<PromotionalBanner> {
    delay(75)
    return listOf(PromotionalBanner("banner-001", "Save more with automatic transfers"))
}

fun main() = runBlocking {
    when (val dashboardState = loadDashboardData()) {
        is DashboardState.Success -> {
            println("Dashboard loaded successfully")
            println("Balance: ${dashboardState.balance.amount} ${dashboardState.balance.currency}")
            println("Transactions: ${dashboardState.transactions.size}")
            println("Promotional banners: ${dashboardState.banners.size}")
        }

        is DashboardState.Error -> {
            println("Dashboard failed to load: ${dashboardState.message}")
        }
    }
}
