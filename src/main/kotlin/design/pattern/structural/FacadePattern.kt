package design.pattern.structural

/**
 * ============================================================================
 * DESIGN PATTERN: FACADE (Structural)
 * ============================================================================
 * 
 * Intent:
 * Provide a unified, simplified, high-level interface to a set of complex 
 * interfaces in a subsystem. A Facade defines an easy-to-use entry point 
 * that shields clients from the inner complexity and interdependence of subsystems.
 * 
 * Real-World Analogy:
 * A Hotel Concierge or a 1-Click Checkout Button:
 * - Without a Concierge: You must call the restaurant for dinner reservations, 
 *   call the valet for your car, call housekeeping for extra towels, and contact 
 *   the tour desk for tickets.
 * - With a Concierge (Facade): You make a single request to the Concierge, who coordinates 
 *   all the distinct departments behind the scenes.
 * 
 * In Kotlin:
 * A Facade class wraps multiple internal services, coordinates their execution flow,
 * and exposes clean, cohesive methods with clear inputs and outputs.
 */

// ----------------------------------------------------------------------------
// 1. COMPLEX SUBSYSTEMS (Individual specialized services)
// ----------------------------------------------------------------------------

class InventoryService {
    private val stock = mutableMapOf(
        "PROD-101" to 15,
        "PROD-102" to 0  // Out of stock
    )

    fun checkStock(productId: String, quantity: Int): Boolean {
        val available = stock.getOrDefault(productId, 0)
        val inStock = available >= quantity
        println("-> [InventoryService] Checking stock for '$productId': ${if (inStock) "Available ($available left)" else "Insufficient stock"}")
        return inStock
    }

    fun deductStock(productId: String, quantity: Int) {
        val current = stock.getOrDefault(productId, 0)
        stock[productId] = current - quantity
        println("-> [InventoryService] Deducted $quantity units of '$productId'. Remaining: ${stock[productId]}")
    }
}

class PaymentService {
    fun processPayment(customerEmail: String, amount: Double): Boolean {
        println("-> [PaymentService] Charging \$$amount to customer: $customerEmail...")
        // Simulating payment gateway validation
        val success = amount > 0
        println("-> [PaymentService] Payment status: ${if (success) "SUCCESS" else "FAILED"}")
        return success
    }
}

class ShippingService {
    fun scheduleDelivery(productId: String, customerAddress: String): String {
        val trackingNumber = "TRK-${(100000..999999).random()}"
        println("-> [ShippingService] Scheduled delivery of '$productId' to '$customerAddress'")
        println("-> [ShippingService] Assigned Tracking #: $trackingNumber")
        return trackingNumber
    }
}

class NotificationService {
    fun sendOrderConfirmation(customerEmail: String, trackingNumber: String) {
        println("-> [NotificationService] Email sent to $customerEmail with tracking details: $trackingNumber")
    }

    fun sendFailureAlert(customerEmail: String, reason: String) {
        println("-> [NotificationService] Email sent to $customerEmail: 'Order failed due to: $reason'")
    }
}

// ----------------------------------------------------------------------------
// 2. THE FACADE (Unified High-Level Interface)
// ----------------------------------------------------------------------------

data class OrderResult(
    val isSuccess: Boolean,
    val message: String,
    val trackingNumber: String? = null
)

class OrderProcessingFacade(
    private val inventoryService: InventoryService = InventoryService(),
    private val paymentService: PaymentService = PaymentService(),
    private val shippingService: ShippingService = ShippingService(),
    private val notificationService: NotificationService = NotificationService()
) {
    /**
     * A single unified method that orchestrates the entire checkout lifecycle:
     * 1. Stock verification
     * 2. Payment processing
     * 3. Inventory deduction
     * 4. Shipping arrangement
     * 5. Customer notification
     */
    fun placeOrder(
        productId: String,
        quantity: Int,
        totalAmount: Double,
        customerEmail: String,
        shippingAddress: String
    ): OrderResult {
        println("\n=== [OrderProcessingFacade] Starting Order Workflow for '$productId' ===")

        // Step 1: Inventory Check
        if (!inventoryService.checkStock(productId, quantity)) {
            notificationService.sendFailureAlert(customerEmail, "Item is out of stock")
            return OrderResult(isSuccess = false, message = "Insufficient inventory")
        }

        // Step 2: Payment Processing
        val paymentSuccess = paymentService.processPayment(customerEmail, totalAmount)
        if (!paymentSuccess) {
            notificationService.sendFailureAlert(customerEmail, "Payment authorization declined")
            return OrderResult(isSuccess = false, message = "Payment failed")
        }

        // Step 3: Deduct Stock
        inventoryService.deductStock(productId, quantity)

        // Step 4: Schedule Shipping
        val trackingNumber = shippingService.scheduleDelivery(productId, shippingAddress)

        // Step 5: Send Order Confirmation
        notificationService.sendOrderConfirmation(customerEmail, trackingNumber)

        println("=== [OrderProcessingFacade] Order Workflow Completed Successfully ===")
        return OrderResult(
            isSuccess = true,
            message = "Order placed successfully",
            trackingNumber = trackingNumber
        )
    }
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    // Client only interacts with the Facade!
    val ecommerceFacade = OrderProcessingFacade()

    // Scenario 1: Successful Order
    val result1 = ecommerceFacade.placeOrder(
        productId = "PROD-101",
        quantity = 2,
        totalAmount = 299.98,
        customerEmail = "alice@example.com",
        shippingAddress = "123 Innovation Way, Tech City"
    )
    println("Client received: $result1")

    // Scenario 2: Out of Stock Order (Fails gracefully at subsystem level)
    val result2 = ecommerceFacade.placeOrder(
        productId = "PROD-102",
        quantity = 1,
        totalAmount = 89.99,
        customerEmail = "bob@example.com",
        shippingAddress = "456 Main St, Metropolis"
    )
    println("Client received: $result2")
}
