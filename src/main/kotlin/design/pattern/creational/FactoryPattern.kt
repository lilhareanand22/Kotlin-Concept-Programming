package design.pattern.creational

/**
 * ============================================================================
 * DESIGN PATTERN: FACTORY (Creational)
 * ============================================================================
 * 
 * Intent:
 * Define an interface or abstract class for creating an object, but let subclasses 
 * or factory logic decide which class to instantiate. It decouples the client 
 * code from concrete implementation classes.
 * 
 * Real-World Analogy:
 * A Notification Dispatch Center or Payment Gateway:
 * The client simply says "Send an alert to user" or "Process payment". The Factory
 * is responsible for creating the concrete channel (Email, SMS, Push Notification)
 * without the client needing to know how each channel is constructed.
 * 
 * In Kotlin:
 * 1. Factory Method Pattern: Using companion objects or factory classes.
 * 2. Kotlin Idiom: Leveraging `sealed class` / `sealed interface` with exhaustive 
 *    `when` expressions, ensuring compile-time safety when new types are added.
 */

// ----------------------------------------------------------------------------
// 1. PRODUCT INTERFACE & CONCRETE IMPLEMENTATIONS
// ----------------------------------------------------------------------------
sealed interface Notification {
    fun send(recipient: String, message: String)
}

class EmailNotification : Notification {
    override fun send(recipient: String, message: String) {
        println("[Email] Sent to <$recipient>: '$message' (via SMTP Server)")
    }
}

class SmsNotification : Notification {
    override fun send(recipient: String, message: String) {
        println("[SMS] Sent to phone <$recipient>: '$message' (via Twilio/Telecom Gateway)")
    }
}

class PushNotification : Notification {
    override fun send(recipient: String, message: String) {
        println("[Push] Sent to device token <$recipient>: '$message' (via Firebase FCM)")
    }
}

// ----------------------------------------------------------------------------
// 2. FACTORY (Encapsulating object creation)
// ----------------------------------------------------------------------------
enum class NotificationType {
    EMAIL,
    SMS,
    PUSH
}

object NotificationFactory {
    // Factory method that instantiates the appropriate product based on type
    fun createNotification(type: NotificationType): Notification {
        return when (type) {
            NotificationType.EMAIL -> EmailNotification()
            NotificationType.SMS -> SmsNotification()
            NotificationType.PUSH -> PushNotification()
        }
    }
}

// ----------------------------------------------------------------------------
// 3. FACTORY METHOD PATTERN (Creator Hierarchy)
// ----------------------------------------------------------------------------
// When different services have their own creation pipeline and business logic:
abstract class NotificationService {
    // The Factory Method
    abstract fun makeNotification(): Notification

    // Core business logic reusing the factory method
    fun notifyUser(recipient: String, message: String) {
        val notification = makeNotification()
        println("Auditing notification delivery...")
        notification.send(recipient, message)
        println("Notification audit complete.\n")
    }
}

class EmailService : NotificationService() {
    override fun makeNotification(): Notification = EmailNotification()
}

class SmsService : NotificationService() {
    override fun makeNotification(): Notification = SmsNotification()
}

class PushService : NotificationService() {
    override fun makeNotification(): Notification = PushNotification()
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== 1. DEMONSTRATING SIMPLE FACTORY ===")
    
    // Client code requests instances without depending on concrete classes
    val emailNotifier = NotificationFactory.createNotification(NotificationType.EMAIL)
    emailNotifier.send("john.doe@example.com", "Your order has been shipped!")

    val smsNotifier = NotificationFactory.createNotification(NotificationType.SMS)
    smsNotifier.send("+1-555-0199", "Your OTP is 482910")

    val pushNotifier = NotificationFactory.createNotification(NotificationType.PUSH)
    pushNotifier.send("device_token_xyz987", "Flash Sale starts in 10 minutes!")

    println("\n=== 2. DEMONSTRATING FACTORY METHOD (CREATOR HIERARCHY) ===")
    
    val service: NotificationService = EmailService()
    service.notifyUser("alice@company.com", "Monthly security report is ready")

    val mobileService: NotificationService = PushService()
    mobileService.notifyUser("device_token_abc123", "New direct message received")
}
