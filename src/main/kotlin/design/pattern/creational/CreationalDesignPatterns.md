# Creational Design Patterns in Kotlin

Package: `design.pattern.creational`

---

## 1. What are Creational Design Patterns?

**Creational Design Patterns** are software design patterns that deal with **object creation mechanisms**. Instead of directly instantiating objects using hardcoded constructors (e.g., `val obj = ConcreteClass()`) throughout the codebase, creational patterns abstract the instantiation process.

### Core Problems They Solve:
1. **Tight Coupling**: Direct instantiation tightly couples client code to specific concrete classes, making future modifications and refactoring difficult.
2. **Complex Instantiation Logic**: Constructing an object may require multi-step initialization, dependency resolution, validation, or conditional branching based on runtime configuration.
3. **Lifecycle Control**: Certain objects must have restricted lifecycles (e.g., only one shared instance, pooled instances, or cloned prototypes).

### Benefits:
- **Loose Coupling**: Code depends on interfaces or abstractions rather than concrete implementations.
- **Maintainability & Extensibility**: Adding new variations does not require modifying existing client code (Open/Closed Principle).
- **Testability**: Dependencies can be substituted with mocks or test doubles with ease.

---

## 2. Pattern 1: Singleton Pattern

### Intent
Ensure a class has **only one instance** throughout the entire application lifecycle and provide a **global point of access** to that instance.

### Real-World Analogy
An **office printer** or a **company's central database**: multiple departments send print jobs or queries to the same shared resource rather than purchasing a dedicated printer or spinning up a separate database instance for every single employee.

### In Kotlin: Idiomatic vs Parameterized

#### A. Idiomatic Kotlin Singleton (`object`)
Kotlin provides first-class support for singletons via the `object` declaration. The Kotlin compiler generates a thread-safe, lazy-initialized instance backed by JVM static initialization.

```kotlin
object AppLogger {
    private val logs = mutableListOf<String>()

    fun log(message: String) {
        val entry = "[LOG - ${System.currentTimeMillis()}]: $message"
        logs.add(entry)
        println(entry)
    }
}
```

#### B. Parameterized Singleton (Double-Checked Locking)
When initialization requires runtime arguments (e.g., a connection URL, API key, or Android `Context`), `object` cannot take constructor parameters. We use a `private constructor` with a thread-safe `companion object`:

```kotlin
class DatabaseConnection private constructor(val databaseUrl: String) {
    companion object {
        @Volatile
        private var INSTANCE: DatabaseConnection? = null

        fun getInstance(databaseUrl: String): DatabaseConnection {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DatabaseConnection(databaseUrl).also { INSTANCE = it }
            }
        }
    }
}
```
- **`@Volatile`**: Guarantees that changes written by one thread are immediately visible to all other threads.
- **Double-Checked Locking**: The outer check bypasses thread synchronization once initialized, preserving high runtime throughput.

### When to Use
- Managing access to shared resources: Thread pools, Database connections, File loggers, Hardware device drivers.
- Caching system configurations that remain consistent app-wide.

### When to Avoid / Trade-offs
- Overusing singletons can turn them into "global state" anti-patterns, making unit testing difficult because state leaks between test cases.
- Prefer Dependency Injection (e.g., Koin, Dagger/Hilt) in large applications to manage singleton lifecycles while keeping components mockable.

👉 **Source Code**: [SingletonPattern.kt](SingletonPattern.kt)

---

## 3. Pattern 2: Factory Pattern (Simple Factory & Factory Method)

### Intent
Define an interface or abstract method for creating an object, but let factory logic or subclasses decide which concrete class to instantiate.

### Real-World Analogy
A **Notification Dispatch Service**:
When an e-commerce order is placed, the checkout system asks the factory: *"Provide a notification channel for SMS"*. The checkout system does not need to know telecom API endpoints, authentication headers, or retry intervals—the Factory encapsulates the construction of `SmsNotification`, `EmailNotification`, or `PushNotification`.

### Key Implementations

#### A. Simple Factory with Kotlin `sealed interface`
```kotlin
sealed interface Notification {
    fun send(recipient: String, message: String)
}

class EmailNotification : Notification { /*...*/ }
class SmsNotification : Notification { /*...*/ }
class PushNotification : Notification { /*...*/ }

object NotificationFactory {
    fun createNotification(type: NotificationType): Notification = when (type) {
        NotificationType.EMAIL -> EmailNotification()
        NotificationType.SMS -> SmsNotification()
        NotificationType.PUSH -> PushNotification()
    }
}
```

#### B. Factory Method Pattern (Creator Hierarchy)
An abstract creator declares the factory method alongside shared workflow logic:
```kotlin
abstract class NotificationService {
    // The Factory Method
    abstract fun makeNotification(): Notification

    fun notifyUser(recipient: String, message: String) {
        val notification = makeNotification()
        // Common auditing / metrics pipeline
        notification.send(recipient, message)
    }
}

class EmailService : NotificationService() {
    override fun makeNotification(): Notification = EmailNotification()
}
```

### Kotlin Superpower: Exhaustive `when`
Because the product is a `sealed interface`, the compiler checks all branches at compile time. If a new notification channel (e.g., `SlackNotification`) is added, the compiler flags missing branches immediately, eliminating runtime crashes.

### When to Use
- When you do not know the exact types or dependencies of the objects your code needs to work with until runtime.
- When you want to decouple creation and initialization logic from business processing logic.

### When to Avoid
- When creating simple, static objects that rarely change (a standard constructor call is clearer and introduces less abstraction).

👉 **Source Code**: [FactoryPattern.kt](FactoryPattern.kt)

---

## 4. Pattern 3: Builder Pattern

### Intent
Separate the construction of a complex object from its representation, allowing the same construction process to produce diverse representations. It eliminates the **"Telescoping Constructor"** anti-pattern (overloaded constructors with dozens of nullable parameters).

### Real-World Analogy
**Custom PC Assembly** or **Custom Subway Sandwich**:
- Core components are mandatory (CPU, RAM / Bread).
- Many components are optional (dedicated GPU, liquid cooling, RGB lighting, extra SSD / sauces, toppings).
- The Builder guides you step-by-step to configure optional components without forcing you to pass `null` for everything you don't need.

### 3 Implementation Approaches in Kotlin

#### 1. Traditional Fluent Builder (OOP Style)
Uses chained setter methods returning the builder instance (`apply`) and ending with `.build()`:
```kotlin
val pc = ComputerBuilder(cpu = "Intel i9", ramGb = 64)
    .setStorage(2048)
    .setGpu("RTX 4090")
    .setLiquidCooling(true)
    .build()
```

#### 2. Idiomatic Kotlin Type-Safe DSL (Lambda with Receiver)
Uses higher-order functions with a receiver (`ComputerDslBuilder.() -> Unit`) to provide clean, declarative block syntax:
```kotlin
fun computer(init: ComputerDslBuilder.() -> Unit): Computer {
    val builder = ComputerDslBuilder()
    builder.init()
    return builder.build()
}

// Client Usage:
val officePc = computer {
    cpu = "AMD Ryzen 5"
    ramGb = 16
    operatingSystem = "Fedora Linux"
}
```

#### 3. Kotlin Named Arguments & Default Parameters
For standard data models, Kotlin language features often satisfy the requirement without creating any builder class:
```kotlin
data class Computer(
    val cpu: String,
    val ramGb: Int = 16,
    val storageGb: Int = 512,
    val gpu: String? = null
)

val laptop = Computer(cpu = "Apple M2", ramGb = 32)
```

### When to Use
- Objects with numerous optional parameters, internal constraints, or multi-stage validation rules.
- Constructing hierarchical or nested structures (e.g., HTML trees, UI layouts, Ktor HTTP request builders).

### When to Avoid
- Simple data classes with 2-4 fields where Kotlin's named and default arguments are cleaner and zero-overhead.

👉 **Source Code**: [BuilderPattern.kt](BuilderPattern.kt)

---

## 5. Summary Matrix: Creational Patterns

| Pattern | Primary Purpose | Kotlin Idiomatic Advantage | Code File |
| :--- | :--- | :--- | :--- |
| **Singleton** | Ensure exactly one instance with global access. | Built-in `object` keyword (thread-safe, lazy-loaded by JVM) + Double-checked locking with `@Volatile`. | [`SingletonPattern.kt`](SingletonPattern.kt) |
| **Factory** | Decouple object creation logic from client consumption. | `sealed interface` / `sealed class` combined with exhaustive `when` expressions. | [`FactoryPattern.kt`](FactoryPattern.kt) |
| **Builder** | Step-by-step construction of complex objects with validation. | Kotlin Type-Safe DSL (`Lambda with Receiver`) and Named/Default arguments. | [`BuilderPattern.kt`](BuilderPattern.kt) |

---

## How to Run the Examples
Each pattern file contains a standalone, runnable `fun main()`:
1. Open the file in your IDE (IntelliJ IDEA / Android Studio).
2. Click the green **Run** arrow next to `fun main()`.
3. Observe the step-by-step console outputs and instance identity verifications.
