package collection

fun main() {
    // 1. Creating a MutableSet
    // mutableSetOf returns a LinkedHashSet by default, which preserves insertion order
    val fruits = mutableSetOf("Apple", "Banana", "Cherry")
    println("Initial MutableSet: $fruits")

    // 2. Adding Elements
    fruits.add("Date") // Adds a single element
    val wasAdded = fruits.add("Apple") // Ignored! Sets do not allow duplicate elements
    println("Was 'Apple' added again? $wasAdded")
    println("After add: $fruits")

    val exoticFruits = listOf("Dragonfruit", "Elderberry", "Fig")
    fruits.addAll(exoticFruits) // Adds multiple elements from another collection
    println("After addAll: $fruits")

    // 3. Removing Elements
    fruits.remove("Banana") // Removes a specific element
    println("After remove: $fruits")

    val fruitsToRemove = setOf("Cherry", "Date")
    fruits.removeAll(fruitsToRemove) // Removes all elements present in the specified collection
    println("After removeAll: $fruits")

    // 4. Retaining Elements
    val fruitsToRetain = setOf("Apple", "Dragonfruit", "Mango")
    fruits.retainAll(fruitsToRetain) // Keeps ONLY the elements that exist in the provided collection
    println("After retainAll: $fruits")

    // 5. Querying and Checking State
    println("Size of set: ${fruits.size}")
    println("Contains 'Apple'?: ${fruits.contains("Apple")}")

    // 6. Clearing the Set
    fruits.clear() // Removes all elements from the MutableSet
    println("After clear, size: ${fruits.size}, isEmpty: ${fruits.isEmpty()}")
}