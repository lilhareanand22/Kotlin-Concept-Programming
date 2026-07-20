package collection

fun main() {
    val numbersMap = mutableMapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)

    // Read operations
    println("Initial map: $numbersMap")
    println("All keys: ${numbersMap.keys}")
    println("All values: ${numbersMap.values}")
    println("All entries: ${numbersMap.entries}")
    println("Size: ${numbersMap.size}")
    println("Value by key key2: ${numbersMap["key2"]}")
    println("getValue(key1): ${numbersMap.getValue("key1")}")
    println("getOrDefault(missing, 0): ${numbersMap.getOrDefault("missing", 0)}")
    println("Contains key2: ${numbersMap.containsKey("key2")}")
    println("Contains value 1: ${numbersMap.containsValue(1)}")
    println("Is empty: ${numbersMap.isEmpty()}")

    // Add and replace operations
    numbersMap["key5"] = 5
    numbersMap.put("key6", 6)
    println("put(key2, 20) replaced: ${numbersMap.put("key2", 20)}")
    println("putIfAbsent(key2, 200): ${numbersMap.putIfAbsent("key2", 200)}")
    println("putIfAbsent(key7, 7): ${numbersMap.putIfAbsent("key7", 7)}")
    numbersMap.putAll(mapOf("key8" to 8, "key9" to 9))
    println("After adding values: $numbersMap")

    // Update operations
    println("replace(key2, 22) replaced: ${numbersMap.replace("key2", 22)}")
    println("replace(key3, 3, 33): ${numbersMap.replace("key3", 3, 33)}")
    numbersMap.compute("key1") { _, value -> value?.plus(10) }
    numbersMap.computeIfAbsent("key10") { 10 }
    numbersMap.computeIfPresent("key4") { _, value -> value * 10 }
    numbersMap.merge("key5", 50) { oldValue, newValue -> oldValue + newValue }
    println("After updating values: $numbersMap")

    // Remove operations
    println("remove(key9): ${numbersMap.remove("key9")}")
    println("remove(key8, 8): ${numbersMap.remove("key8", 8)}")
    println("After removing values: $numbersMap")

    // Mutable views: changes to these collections change the map.
    numbersMap.keys.remove("key10")
    numbersMap.values.remove(7) // Removes the entry whose value is 7.
    numbersMap.entries.removeIf { (key, value) -> key == "key6" && value == 6 }
    println("After changing map views: $numbersMap")

    numbersMap.clear()
    println("Map after clear: $numbersMap")
    println("Is empty after clear: ${numbersMap.isEmpty()}")
}
