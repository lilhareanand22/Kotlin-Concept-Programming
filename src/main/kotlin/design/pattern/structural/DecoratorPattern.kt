package design.pattern.structural

/**
 * ============================================================================
 * DESIGN PATTERN: DECORATOR (Structural)
 * ============================================================================
 * 
 * Intent:
 * Attach additional responsibilities or behaviors to an object dynamically 
 * without modifying its structure or altering the original class.
 * Provides a flexible, composition-based alternative to subclassing.
 * 
 * Real-World Analogy:
 * Ordering Coffee at a Café or Dressing for Winter:
 * - Base: Simple Coffee ($2.00)
 * - Wrap with Milk decorator: (+ $0.50, "with Milk")
 * - Wrap with Caramel decorator: (+ $0.75, "with Caramel")
 * - Wrap with Whipped Cream decorator: (+ $0.50, "with Whipped Cream")
 * Instead of creating 30 rigid subclasses (CoffeeWithMilkAndSugar, CoffeeWithCaramel, etc.),
 * you dynamically stack wrappers around the core object at runtime.
 * 
 * In Kotlin:
 * Kotlin provides first-class support for the Decorator pattern through 
 * **Class Delegation** using the `by` keyword. 
 * This eliminates the tedious forwarding boilerplate required in Java.
 */

// ----------------------------------------------------------------------------
// 1. COMPONENT INTERFACE
// ----------------------------------------------------------------------------
interface Coffee {
    fun getCost(): Double
    fun getDescription(): String
}

// ----------------------------------------------------------------------------
// 2. CONCRETE COMPONENT (The Base Object)
// ----------------------------------------------------------------------------
class SimpleCoffee : Coffee {
    override fun getCost(): Double = 2.00
    override fun getDescription(): String = "Simple Espresso"
}

// ----------------------------------------------------------------------------
// 3. DECORATORS USING KOTLIN'S `by` KEYWORD (Class Delegation)
// ----------------------------------------------------------------------------

// Milk Decorator: wraps any Coffee, delegates everything by default,
// and overrides only what it alters!
class MilkDecorator(private val coffee: Coffee) : Coffee by coffee {
    override fun getCost(): Double {
        return coffee.getCost() + 0.50
    }

    override fun getDescription(): String {
        return "${coffee.getDescription()}, with Steamed Milk"
    }
}

// Caramel Decorator
class CaramelDecorator(private val coffee: Coffee) : Coffee by coffee {
    override fun getCost(): Double {
        return coffee.getCost() + 0.75
    }

    override fun getDescription(): String {
        return "${coffee.getDescription()}, with Caramel Drizzle"
    }
}

// Whipped Cream Decorator
class WhipDecorator(private val coffee: Coffee) : Coffee by coffee {
    override fun getCost(): Double {
        return coffee.getCost() + 0.50
    }

    override fun getDescription(): String {
        return "${coffee.getDescription()}, with Whipped Cream"
    }
}

// ----------------------------------------------------------------------------
// 4. ANOTHER PRACTICAL REAL-WORLD EXAMPLE: DATA ENCRYPTION & LOGGING
// ----------------------------------------------------------------------------
interface DataSource {
    fun writeData(data: String)
    fun readData(): String
}

class FileDataSource(private val filename: String) : DataSource {
    private var content: String = ""

    override fun writeData(data: String) {
        println("-> [Disk] Writing to file '$filename': $data")
        content = data
    }

    override fun readData(): String {
        println("<- [Disk] Reading from file '$filename'")
        return content
    }
}

// Encryption Decorator: encrypts before write, decrypts after read
class EncryptionDecorator(private val source: DataSource) : DataSource by source {
    override fun writeData(data: String) {
        val encrypted = data.reversed() // Simulating encryption
        println("-> [EncryptionDecorator] Encrypting data: '$data' -> '$encrypted'")
        source.writeData(encrypted)
    }

    override fun readData(): String {
        val raw = source.readData()
        val decrypted = raw.reversed() // Simulating decryption
        println("<- [EncryptionDecorator] Decrypting raw data: '$raw' -> '$decrypted'")
        return decrypted
    }
}

// Logging Decorator: logs operations with timestamp
class LoggingDecorator(private val source: DataSource) : DataSource by source {
    override fun writeData(data: String) {
        println("-> [LoggingDecorator] Log: writeData called at ${System.currentTimeMillis()}")
        source.writeData(data)
    }

    override fun readData(): String {
        println("<- [LoggingDecorator] Log: readData called at ${System.currentTimeMillis()}")
        return source.readData()
    }
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== 1. COFFEE SHOP DECORATOR DEMO ===")

    // Base coffee
    var myCoffee: Coffee = SimpleCoffee()
    println("${myCoffee.getDescription()} => \$${myCoffee.getCost()}")

    // Decorate with Milk
    myCoffee = MilkDecorator(myCoffee)
    println("${myCoffee.getDescription()} => \$${myCoffee.getCost()}")

    // Decorate with Caramel
    myCoffee = CaramelDecorator(myCoffee)
    println("${myCoffee.getDescription()} => \$${myCoffee.getCost()}")

    // Decorate with Whipped Cream
    myCoffee = WhipDecorator(myCoffee)
    println("${myCoffee.getDescription()} => \$${myCoffee.getCost()}")

    println("\n=== 2. DATA SOURCE PIPELINE DECORATOR DEMO ===")

    // Stack decorators: FileDataSource wrapped with Encryption, then wrapped with Logging
    val securedDataSource: DataSource = LoggingDecorator(
        EncryptionDecorator(
            FileDataSource("user_credentials.txt")
        )
    )

    // Writing through the decorator chain
    securedDataSource.writeData("SecretPassword123")

    println()

    // Reading through the decorator chain
    val result = securedDataSource.readData()
    println("Final Decoded Result: $result")
}
