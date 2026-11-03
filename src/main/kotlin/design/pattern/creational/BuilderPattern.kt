package design.pattern.creational

/**
 * ============================================================================
 * DESIGN PATTERN: BUILDER (Creational)
 * ============================================================================
 * 
 * Intent:
 * Separate the construction of a complex object from its representation so that
 * the same construction process can create different representations.
 * It prevents the "Telescoping Constructor" anti-pattern (constructors with 10+ params).
 * 
 * Real-World Analogy:
 * Custom PC Assembly or Subway Sandwich:
 * You choose a base (CPU / Bread), then customize optional parts (GPU, extra RAM,
 * liquid cooling, sauces, toppings) step-by-step before finalizing the build.
 * 
 * In Kotlin:
 * 1. Traditional OOP Builder: Fluent chaining methods (.setCpu().setRam().build()).
 * 2. Kotlin Named & Default Arguments: Replaces simple builders for data classes.
 * 3. Idiomatic Kotlin DSL Builder: Uses higher-order functions with receivers 
 *    (Lambda with Receiver) to create declarative, type-safe configuration blocks.
 */

// ----------------------------------------------------------------------------
// 1. COMPLEX PRODUCT
// ----------------------------------------------------------------------------
data class Computer(
    val cpu: String,
    val ramGb: Int,
    val storageGb: Int,
    val gpu: String?,
    val hasLiquidCooling: Boolean,
    val operatingSystem: String
) {
    override fun toString(): String {
        return """
            Computer Configuration:
            - CPU: $cpu
            - RAM: ${ramGb}GB
            - Storage: ${storageGb}GB SSD
            - GPU: ${gpu ?: "Integrated Graphics"}
            - Cooling: ${if (hasLiquidCooling) "Liquid Cooling" else "Air Cooling"}
            - OS: $operatingSystem
        """.trimIndent()
    }
}

// ----------------------------------------------------------------------------
// 2. TRADITIONAL OOP BUILDER (Fluent Chaining)
// ----------------------------------------------------------------------------
class ComputerBuilder(
    // Required parameters
    private val cpu: String,
    private val ramGb: Int
) {
    // Optional parameters with sensible defaults
    private var storageGb: Int = 256
    private var gpu: String? = null
    private var hasLiquidCooling: Boolean = false
    private var operatingSystem: String = "Ubuntu Linux"

    fun setStorage(storageGb: Int): ComputerBuilder = apply {
        this.storageGb = storageGb
    }

    fun setGpu(gpu: String): ComputerBuilder = apply {
        this.gpu = gpu
    }

    fun setLiquidCooling(enabled: Boolean): ComputerBuilder = apply {
        this.hasLiquidCooling = enabled
    }

    fun setOperatingSystem(os: String): ComputerBuilder = apply {
        this.operatingSystem = os
    }

    fun build(): Computer {
        // Validation logic can run here before instantiation
        require(ramGb >= 4) { "RAM must be at least 4GB for a valid computer build" }
        return Computer(
            cpu = cpu,
            ramGb = ramGb,
            storageGb = storageGb,
            gpu = gpu,
            hasLiquidCooling = hasLiquidCooling,
            operatingSystem = operatingSystem
        )
    }
}

// ----------------------------------------------------------------------------
// 3. IDIOMATIC KOTLIN DSL BUILDER (Lambda with Receiver)
// ----------------------------------------------------------------------------
class ComputerDslBuilder {
    var cpu: String = ""
    var ramGb: Int = 8
    var storageGb: Int = 256
    var gpu: String? = null
    var hasLiquidCooling: Boolean = false
    var operatingSystem: String = "Windows 11"

    fun build(): Computer {
        require(cpu.isNotBlank()) { "CPU must not be blank" }
        return Computer(cpu, ramGb, storageGb, gpu, hasLiquidCooling, operatingSystem)
    }
}

// DSL entry function using lambda with receiver: ComputerDslBuilder.() -> Unit
fun computer(init: ComputerDslBuilder.() -> Unit): Computer {
    val builder = ComputerDslBuilder()
    builder.init() // execute configuration lambda inside builder scope
    return builder.build()
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== 1. TRADITIONAL FLUENT BUILDER ===")
    
    val gamingPc = ComputerBuilder(cpu = "Intel Core i9-14900K", ramGb = 64)
        .setStorage(2048)
        .setGpu("NVIDIA GeForce RTX 4090")
        .setLiquidCooling(true)
        .setOperatingSystem("Windows 11 Pro")
        .build()

    println(gamingPc)

    println("\n=== 2. IDIOMATIC KOTLIN DSL BUILDER ===")

    val officePc = computer {
        cpu = "AMD Ryzen 5 7600"
        ramGb = 16
        storageGb = 512
        operatingSystem = "Fedora Linux"
        // gpu and liquidCooling stay as defaults
    }

    println(officePc)

    println("\n=== 3. KOTLIN BUILT-IN ALTERNATIVE: NAMED & DEFAULT ARGUMENTS ===")
    
    // For simpler objects, Kotlin data classes with default arguments eliminate 
    // the need for a separate Builder class altogether:
    val miniServer = Computer(
        cpu = "Apple M2 Pro",
        ramGb = 32,
        storageGb = 1024,
        gpu = null,
        hasLiquidCooling = false,
        operatingSystem = "macOS Sequoia"
    )
    println(miniServer)
}
