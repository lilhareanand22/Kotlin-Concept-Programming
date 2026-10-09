package collection

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
    println(numbersMap.get("one"))
    println(numbersMap["one"])
    println(numbersMap.getOrDefault("four", 10))
    println(numbersMap["five"])
    val nullableMap = mapOf("one" to 1, "two" to null)
    println(nullableMap.getOrElseIfNull("two") { 0 })
    println(nullableMap.getOrElseIfMissing("two") { 0 })

    // To get keys and values
    println(numbersMap.keys)
    println(numbersMap.values)

    // Filters
    val numbersMap1 = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    val filteredMap = numbersMap1.filter { (key, value) -> key.endsWith("1") && value > 10}
    println(filteredMap)

    val filteredKeysMap = numbersMap1.filterKeys { it.endsWith("1") }
    val filteredValuesMap = numbersMap1.filterValues { it < 10 }
    println(filteredKeysMap)
    println(filteredValuesMap)

    // Plus and Minus Operation
    println(numbersMap + Pair("four", 4))
    println(numbersMap + Pair("one", 10))
    println(numbersMap + mapOf("five" to 5, "one" to 11))

    println(numbersMap - "one")
    println(numbersMap - listOf("two", "four"))

    // Add and update entries
    val numbersMap2 = mutableMapOf("one" to 1, "two" to 2)
    numbersMap2.put("three", 3)
    println(numbersMap2)
    numbersMap2.putAll(setOf("four" to 4, "five" to 5))
    println(numbersMap2)


    val mapForNull = mutableMapOf<String, Int?>("one" to null)
    val mapForMissing = mutableMapOf<String, Int?>("one" to null)

// Replaces the value if "one" has a null value
    mapForNull.getOrPutIfNull("one") { 1 }

    println(mapForNull)
// {one=1}

// Keeps the null value because "one" exists in the map
    mapForMissing.getOrPutIfMissing("one") { 1 }

    println(mapForMissing)

 // Removed Entries
    val numbersMap4 = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
    numbersMap4.remove("one")
    println(numbersMap4)
    numbersMap4.remove("three", 4)            //doesn't remove anything
    println(numbersMap4)

    numbersMap4.keys.remove("one")
    println(numbersMap4)
    numbersMap4.values.remove(3)
    println(numbersMap4)
    for((id, name) in numbersMap4) {
        println("$id: $name")
    }
}