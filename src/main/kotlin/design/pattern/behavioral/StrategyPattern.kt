package design.pattern.behavioral

/**
 * ============================================================================
 * DESIGN PATTERN: STRATEGY (Behavioral)
 * ============================================================================
 * 
 * Intent:
 * Define a family of algorithms, encapsulate each one, and make them interchangeable.
 * Strategy lets the algorithm vary independently from the clients that use it.
 * 
 * Real-World Analogy:
 * Navigation App or Checkout Payment:
 * - A Maps app offers different route strategies: Driving (fastest road), Walking (shortest path),
 *   or Cycling (bike lanes). The destination is the same, but the calculation strategy changes.
 * - At an e-commerce checkout, you choose your payment strategy: Credit Card, PayPal, or UPI.
 * 
 * In Kotlin:
 * 1. Classic Strategy: Define a Strategy interface and concrete implementation classes.
 * 2. Idiomatic Kotlin Strategy: Use First-Class Functions and Lambdas `(Double) -> Double`
 *    instead of single-method interfaces, eliminating unnecessary class boilerplate.
 */

// ----------------------------------------------------------------------------
// 1. CLASSIC STRATEGY: PAYMENT METHODS
// ----------------------------------------------------------------------------

interface PaymentStrategy {
    fun pay(amount: Double)
}

class CreditCardPayment(
    private val cardNumber: String,
    private val cardHolder: String
) : PaymentStrategy {
    override fun pay(amount: Double) {
        val masked = "****-****-****-" + cardNumber.takeLast(4)
        println("-> [CreditCard] Paid \$$amount using card $masked ($cardHolder)")
    }
}

class PayPalPayment(private val email: String) : PaymentStrategy {
    override fun pay(amount: Double) {
        println("-> [PayPal] Paid \$$amount using PayPal account: $email")
    }
}

class UpiPayment(private val upiId: String) : PaymentStrategy {
    override fun pay(amount: Double) {
        println("-> [UPI] Paid \$$amount using VPA: $upiId")
    }
}

// Context: Uses the strategy
class ShoppingCart(private var paymentStrategy: PaymentStrategy) {
    private val items = mutableListOf<Pair<String, Double>>()

    fun addItem(name: String, price: Double) {
        items.add(name to price)
    }

    // Change strategy dynamically at runtime
    fun setPaymentStrategy(newStrategy: PaymentStrategy) {
        this.paymentStrategy = newStrategy
    }

    fun checkout() {
        val total = items.sumOf { it.second }
        println("\nCheckout Summary: ${items.size} items. Total: \$$total")
        paymentStrategy.pay(total)
    }
}

// ----------------------------------------------------------------------------
// 2. IDIOMATIC KOTLIN STRATEGY: HIGHER-ORDER FUNCTIONS / LAMBDAS
// ----------------------------------------------------------------------------
// Instead of creating an interface and 5 classes for simple calculation rules,
// Kotlin lets you pass a function type: (originalPrice: Double) -> Double

typealias DiscountStrategy = (Double) -> Double

// Common discount algorithms defined cleanly as functions:
val noDiscount: DiscountStrategy = { price -> price }
val percentageDiscount: (Double) -> DiscountStrategy = { percent -> { price -> price * (1.0 - percent / 100.0) } }
val flatCouponDiscount: (Double) -> DiscountStrategy = { discountAmount -> { price -> (price - discountAmount).coerceAtLeast(0.0) } }

class ProductPricing(val originalPrice: Double) {
    fun calculateFinalPrice(discountStrategy: DiscountStrategy): Double {
        return discountStrategy(originalPrice)
    }
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== 1. DEMONSTRATING CLASSIC STRATEGY (SWAPPABLE PAYMENT) ===")

    val cart = ShoppingCart(CreditCardPayment("1234567812345678", "Alice Smith"))
    cart.addItem("Mechanical Keyboard", 120.00)
    cart.addItem("Wireless Mouse", 50.00)

    // Pay with Credit Card
    cart.checkout()

    // Customer decides to change payment method to UPI before placing another order
    cart.addItem("USB-C Hub", 35.00)
    cart.setPaymentStrategy(UpiPayment("alice@oksbi"))
    cart.checkout()

    println("\n=== 2. DEMONSTRATING KOTLIN FUNCTIONAL STRATEGY (DISCOUNTS) ===")

    val product = ProductPricing(originalPrice = 200.0)
    println("Original Price: \$${product.originalPrice}")

    // Strategy A: No discount
    val priceRegular = product.calculateFinalPrice(noDiscount)
    println("Price with No Discount: \$$priceRegular")

    // Strategy B: 20% Holiday Discount
    val priceHoliday = product.calculateFinalPrice(percentageDiscount(20.0))
    println("Price with 20% Holiday Discount: \$$priceHoliday")

    // Strategy C: Flat $30 Off Coupon
    val priceCoupon = product.calculateFinalPrice(flatCouponDiscount(30.0))
    println("Price with \$30 Flat Coupon: \$$priceCoupon")

    // Strategy D: Ad-hoc custom inline lambda strategy
    val priceClearance = product.calculateFinalPrice { price -> price * 0.50 }
    println("Price with Custom 50% Clearance: \$$priceClearance")
}
