package design.pattern.behavioral

import java.util.Stack

/**
 * ============================================================================
 * DESIGN PATTERN: COMMAND (Behavioral)
 * ============================================================================
 * 
 * Intent:
 * Encapsulate a request as an object, thereby letting you parameterize clients 
 * with different requests, queue or log requests, and support undoable operations.
 * 
 * Real-World Analogy:
 * A Restaurant Waiter or a Smart Home Remote Control:
 * - When you place an order at a restaurant, the waiter writes it on an Order Slip.
 *   The order slip encapsulates your request. It can be queued, passed to the chef, 
 *   or cancelled before preparation.
 * - On a smart home remote, buttons trigger commands. A universal remote can also
 *   have an "Undo" button that reverses the last executed action.
 * 
 * In Kotlin:
 * 1. Command Interface: with `execute()` and `undo()` methods.
 * 2. Receivers: The actual hardware/devices performing the real work (Light, AC).
 * 3. Invoker: Holds command history stacks to provide seamless Undo/Redo support.
 */

// ----------------------------------------------------------------------------
// 1. COMMAND INTERFACE
// ----------------------------------------------------------------------------
interface Command {
    fun execute()
    fun undo()
}

// ----------------------------------------------------------------------------
// 2. RECEIVERS (The actual business/hardware objects that perform actions)
// ----------------------------------------------------------------------------

class SmartLight(val room: String) {
    var isOn: Boolean = false
        private set

    fun turnOn() {
        isOn = true
        println("-> [SmartLight: $room] Light is turned ON")
    }

    fun turnOff() {
        isOn = false
        println("-> [SmartLight: $room] Light is turned OFF")
    }
}

class AirConditioner(val room: String) {
    var temperature: Int = 24
        private set

    fun setTemp(temp: Int) {
        val old = temperature
        temperature = temp
        println("-> [AC: $room] Temperature changed from ${old}°C to ${temp}°C")
    }
}

// ----------------------------------------------------------------------------
// 3. CONCRETE COMMANDS
// ----------------------------------------------------------------------------

class LightOnCommand(private val light: SmartLight) : Command {
    override fun execute() {
        light.turnOn()
    }

    override fun undo() {
        light.turnOff()
    }
}

class LightOffCommand(private val light: SmartLight) : Command {
    override fun execute() {
        light.turnOff()
    }

    override fun undo() {
        light.turnOn()
    }
}

class SetAcTemperatureCommand(
    private val ac: AirConditioner,
    private val targetTemp: Int
) : Command {
    private var previousTemp: Int = ac.temperature

    override fun execute() {
        previousTemp = ac.temperature
        ac.setTemp(targetTemp)
    }

    override fun undo() {
        println("-> [Undo Action] Restoring previous AC temperature...")
        ac.setTemp(previousTemp)
    }
}

// Macro Command: Combines multiple commands into a single execution
class MacroCommand(private val commands: List<Command>) : Command {
    override fun execute() {
        println(">> Executing Macro Sequence...")
        commands.forEach { it.execute() }
    }

    override fun undo() {
        println(">> Undoing Macro Sequence in Reverse...")
        commands.reversed().forEach { it.undo() }
    }
}

// ----------------------------------------------------------------------------
// 4. INVOKER (Smart Remote with Undo & Redo History)
// ----------------------------------------------------------------------------

class SmartRemoteControl {
    private val undoHistory = Stack<Command>()
    private val redoHistory = Stack<Command>()

    fun executeCommand(command: Command) {
        command.execute()
        undoHistory.push(command)
        redoHistory.clear() // Clear redo history upon new command
    }

    fun pressUndo() {
        if (undoHistory.isNotEmpty()) {
            val lastCommand = undoHistory.pop()
            println("\n[Remote Control] UNDO Pressed:")
            lastCommand.undo()
            redoHistory.push(lastCommand)
        } else {
            println("[Remote Control] Nothing to undo.")
        }
    }

    fun pressRedo() {
        if (redoHistory.isNotEmpty()) {
            val redoCommand = redoHistory.pop()
            println("\n[Remote Control] REDO Pressed:")
            redoCommand.execute()
            undoHistory.push(redoCommand)
        } else {
            println("[Remote Control] Nothing to redo.")
        }
    }
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== 1. DEMONSTRATING COMMAND PATTERN WITH UNDO/REDO ===")

    val remote = SmartRemoteControl()

    val livingRoomLight = SmartLight("Living Room")
    val livingRoomAc = AirConditioner("Living Room")

    val lightOn = LightOnCommand(livingRoomLight)
    val lightOff = LightOffCommand(livingRoomLight)
    val acCoolDown = SetAcTemperatureCommand(livingRoomAc, 18)

    // Execute commands
    println("--- Executing Commands ---")
    remote.executeCommand(lightOn)
    remote.executeCommand(acCoolDown)
    remote.executeCommand(lightOff)

    // Test Undo operations
    remote.pressUndo() // Undoes Light Off -> Turns light back ON
    remote.pressUndo() // Undoes AC 18°C -> Restores AC to 24°C

    // Test Redo operations
    remote.pressRedo() // Redoes AC to 18°C

    println("\n=== 2. DEMONSTRATING MACRO COMMAND (SCENE MODE) ===")

    val bedroomLight = SmartLight("Bedroom")
    val bedroomAc = AirConditioner("Bedroom")

    // "Sleep Mode" Macro Command: Turn off lights, set AC to comfortable sleep temp (22°C)
    val sleepMode = MacroCommand(
        listOf(
            LightOffCommand(bedroomLight),
            SetAcTemperatureCommand(bedroomAc, 22)
        )
    )

    remote.executeCommand(sleepMode)

    // Undo the entire macro with one click
    remote.pressUndo()
}
