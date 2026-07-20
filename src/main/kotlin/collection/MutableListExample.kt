package collection

fun main() {
    // Creating a MutableList
    val fruits = mutableListOf("Apple", "Banana", "Cherry")
    println("Initial list: $fruits")

    // 1. Adding elements
    fruits.add("Date") // Adds at the end
    fruits.add(1, "Blueberry") // Adds at a specific index
    val moreFruits = listOf("Elderberry", "Fig")
    fruits.addAll(moreFruits) // Adds a collection
    println("After adding elements: $fruits")

    // 2. Updating elements
    fruits[0] = "Apricot" // Replaces element at index 0
    fruits.set(2, "Blackberry") // Replaces element at index 2
    println("After updating elements: $fruits")

    // 3. Removing elements
    fruits.remove("Fig") // Removes the first occurrence of the specified element
    fruits.removeAt(1) // Removes the element at the specified index
    fruits.removeFirst() // Removes the first element
    fruits.removeLast() // Removes the last element
    println("After removing elements: $fruits")

    // 4. Checking existence
    val hasBanana = fruits.contains("Banana")
    println("Contains Banana? $hasBanana")

    // 5. Finding elements
    fruits.add("Blackberry") // Adding another Blackberry to demonstrate lastIndexOf
    val firstBlackberryIndex = fruits.indexOf("Blackberry")
    val lastBlackberryIndex = fruits.lastIndexOf("Blackberry")
    println("First Blackberry index: $firstBlackberryIndex")
    println("Last Blackberry index: $lastBlackberryIndex")
    
    // 6. Clearing the list
    fruits.clear()
    println("After clearing: $fruits")
}
