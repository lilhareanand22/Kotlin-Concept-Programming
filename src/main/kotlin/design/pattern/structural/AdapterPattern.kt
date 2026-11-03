package design.pattern.structural

/**
 * ============================================================================
 * DESIGN PATTERN: ADAPTER (Structural)
 * ============================================================================
 * 
 * Intent:
 * Convert the interface of a class into another interface clients expect.
 * The Adapter lets classes work together that couldn't otherwise because of
 * incompatible interfaces.
 * 
 * Real-World Analogy:
 * A Travel Plug Adapter or USB-C to 3.5mm Headphone Jack Dongle:
 * Your laptop only has a USB-C port (Target interface), while your headphones
 * use a 3.5mm audio jack (Adaptee). The dongle (Adapter) bridges the physical
 * and electrical differences without altering the laptop or the headphones.
 * 
 * In Kotlin:
 * 1. Classic Object Adapter: Composition-based wrapper implementing the target interface.
 * 2. Idiomatic Kotlin Adapter: Extension functions or SAM interfaces for lightweight adaptations.
 */

// ----------------------------------------------------------------------------
// 1. TARGET INTERFACE (What our modern application expects)
// ----------------------------------------------------------------------------
interface PaymentGateway {
    fun processPayment(amountInDollars: Double, customerEmail: String): Boolean
}

// Modern in-house payment implementation conforming directly to target
class StandardPaymentGateway : PaymentGateway {
    override fun processPayment(amountInDollars: Double, customerEmail: String): Boolean {
        println("[StandardGateway] Successfully processed \$$amountInDollars for $customerEmail")
        return true
    }
}

// ----------------------------------------------------------------------------
// 2. ADAPTEE (Incompatible 3rd-Party or Legacy SDK)
// ----------------------------------------------------------------------------
// Notice the differences:
// - Takes amount in CENTS (Long) instead of DOLLARS (Double)
// - Uses a different method name `makeCharge`
// - Returns an HTTP-style status code (200 = OK) instead of Boolean
class LegacyStripeSdk {
    fun makeCharge(amountInCents: Long, email: String): Int {
        println("[LegacyStripeSdk] Charging $amountInCents cents to $email via legacy API...")
        // Simulating successful 200 HTTP response
        return 200
    }
}

// Another legacy system with XML-style payload
class OldBankTransferService {
    fun sendWireTransfer(xmlPayload: String): String {
        println("[OldBankTransfer] Executing wire transfer with payload: $xmlPayload")
        return "STATUS:SUCCESS:TXN_9918"
    }
}

// ----------------------------------------------------------------------------
// 3. THE ADAPTER (Bridging the gap)
// ----------------------------------------------------------------------------

// Adapter for LegacyStripeSdk
class StripePaymentAdapter(
    private val legacySdk: LegacyStripeSdk
) : PaymentGateway {

    override fun processPayment(amountInDollars: Double, customerEmail: String): Boolean {
        // Step 1: Translate domain units (Dollars -> Cents)
        val amountInCents = (amountInDollars * 100).toLong()

        // Step 2: Delegate call to the adaptee's incompatible method
        val statusCode = legacySdk.makeCharge(amountInCents, customerEmail)

        // Step 3: Translate response to modern boolean expectation
        return statusCode == 200
    }
}

// Adapter for OldBankTransferService
class BankTransferAdapter(
    private val bankService: OldBankTransferService
) : PaymentGateway {

    override fun processPayment(amountInDollars: Double, customerEmail: String): Boolean {
        val payload = "<transfer><amount>$amountInDollars</amount><user>$customerEmail</user></transfer>"
        val response = bankService.sendWireTransfer(payload)
        return response.contains("STATUS:SUCCESS")
    }
}

// ----------------------------------------------------------------------------
// 4. CLIENT CODE
// ----------------------------------------------------------------------------
// Client code depends strictly on the target interface (PaymentGateway)
class CheckoutService(private val paymentGateway: PaymentGateway) {
    fun checkout(cartTotal: Double, userEmail: String) {
        println("\nInitiating checkout for $userEmail (Total: \$$cartTotal)...")
        val success = paymentGateway.processPayment(cartTotal, userEmail)
        if (success) {
            println("Checkout successful! Order confirmed.")
        } else {
            println("Checkout failed! Payment rejected.")
        }
    }
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== DEMONSTRATING ADAPTER PATTERN ===")

    // Scenario A: Using modern standard gateway directly
    val standardGateway = StandardPaymentGateway()
    val checkoutA = CheckoutService(standardGateway)
    checkoutA.checkout(49.99, "alice@example.com")

    // Scenario B: Using Legacy Stripe SDK adapted to modern PaymentGateway
    val legacyStripe = LegacyStripeSdk()
    val stripeAdapter = StripePaymentAdapter(legacyStripe)
    val checkoutB = CheckoutService(stripeAdapter)
    checkoutB.checkout(129.50, "bob@example.com")

    // Scenario C: Using Old Bank Wire Transfer adapted to modern PaymentGateway
    val legacyBank = OldBankTransferService()
    val bankAdapter = BankTransferAdapter(legacyBank)
    val checkoutC = CheckoutService(bankAdapter)
    checkoutC.checkout(1500.00, "charlie@company.com")
}
