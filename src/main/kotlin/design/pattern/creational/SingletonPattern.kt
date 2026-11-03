package design.pattern.creational

/**
 * ============================================================================
 * DESIGN PATTERN: SINGLETON (Creational)
 * ============================================================================
 * 
 * Intent:
 * Ensure a class has only one instance and provide a global point of access to it.
 * 
 * Real-World Analogy:
 * The office printer or a nation's government: there is only one official instance 
 * serving everyone. Multiple departments share the same printer queue.
 * 
 * In Kotlin:
 * 1. Idiomatic Singleton: Using the `object` declaration (Thread-safe, lazy-loaded by JVM).
 * 2. Parameterized Singleton: When an instance requires parameters (e.g., config/url),
 *    we use a private constructor with a thread-safe Double-Checked Locking companion object.
 */

// ----------------------------------------------------------------------------
// 1. IDIOMATIC KOTLIN SINGLETON (No parameters)
// ----------------------------------------------------------------------------
object AppLogger {
    private val logs = mutableListOf<String>()

    init {
        println(">> [AppLogger] Initialized (Only happens once upon first access)")
    }

    fun log(message: String) {
        val entry = "[LOG - ${System.currentTimeMillis()}]: $message"
        logs.add(entry)
        println(entry)
    }

    fun getLogCount(): Int = logs.size
}

// ----------------------------------------------------------------------------
// 2. PARAMETERIZED SINGLETON (Double-Checked Locking)
// ----------------------------------------------------------------------------
// Useful when the singleton needs configuration arguments (like DB URL, Context, etc.)
class DatabaseConnection private constructor(val databaseUrl: String) {

    init {
        println(">> [DatabaseConnection] Connected to: $databaseUrl")
    }

    fun query(sql: String) {
        println("Executing SQL on [$databaseUrl]: $sql")
    }

    companion object {
        // @Volatile ensures changes made by one thread are immediately visible to other threads
        @Volatile
        private var INSTANCE: DatabaseConnection? = null

        fun getInstance(databaseUrl: String): DatabaseConnection {
            // First check (no lock for performance)
            return INSTANCE ?: synchronized(this) {
                // Second check (inside synchronized block)
                INSTANCE ?: DatabaseConnection(databaseUrl).also { INSTANCE = it }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== 1. DEMONSTRATING KOTLIN 'object' SINGLETON ===")
    
    // First access initializes the object
    AppLogger.log("User logged in with ID: 101")
    
    // Second access reuses the same instance
    AppLogger.log("User navigated to Profile Screen")
    
    val loggerRef1 = AppLogger
    val loggerRef2 = AppLogger

    println("loggerRef1 === loggerRef2: ${loggerRef1 === loggerRef2}") // true: same memory reference
    println("Total logs recorded: ${AppLogger.getLogCount()}")

    println("\n=== 2. DEMONSTRATING PARAMETERIZED SINGLETON ===")

    val db1 = DatabaseConnection.getInstance("jdbc:mysql://localhost:3306/prod_db")
    val db2 = DatabaseConnection.getInstance("jdbc:mysql://localhost:3306/another_db")

    db1.query("SELECT * FROM users")
    db2.query("SELECT * FROM orders")

    // db2 points to the SAME instance as db1; initial config remains
    println("db1 === db2: ${db1 === db2}") // true
    println("db2 databaseUrl: ${db2.databaseUrl}") // Still prod_db
}
