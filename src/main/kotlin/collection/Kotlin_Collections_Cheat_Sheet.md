# Kotlin Collections Cheat Sheet (Interview + Android)

> Covers the most important Kotlin Collection topics for Senior Android
> interviews.

------------------------------------------------------------------------

# Collection Hierarchy

``` text
Iterable
 ├── Collection
 │    ├── List
 │    ├── Set
 │    └── MutableCollection
 └── Map (Not a Collection)
```

  ---------------------------------------------------------------------------
  Type     Ordered          Duplicates        Index      Default Impl
  -------- ---------------- ----------------- ---------- --------------------
  List     ✅               ✅                ✅         ArrayList

  Set      Depends          ❌                ❌         LinkedHashSet
           (LinkedHashSet                                
           preserves                                     
           insertion order)                              

  Map      Keys ordered in  Keys ❌ Values ✅ Key lookup LinkedHashMap
           LinkedHashMap                                 
  ---------------------------------------------------------------------------

------------------------------------------------------------------------

# List

-   Ordered collection
-   Index based
-   Duplicates allowed
-   Null allowed

Common APIs

``` kotlin
listOf()
mutableListOf()

get()
add()
remove()
contains()
```

Complexity

  Operation              Complexity
  ---------------------- ----------------
  get(index)             O(1)
  add(end)               O(1) amortized
  insert/remove middle   O(n)
  contains               O(n)

Android: - RecyclerView - Compose LazyColumn - API responses

------------------------------------------------------------------------

# Set

-   Unique elements
-   Fast lookup

Default: LinkedHashSet

Complexity

  add    contains   remove
  ------ ---------- --------
  O(1)   O(1)       O(1)

Android: - BLE devices - Tags - Permission names

------------------------------------------------------------------------

# Map

Stores Key → Value

Default: LinkedHashMap

``` kotlin
mutableMapOf()
mapOf()
```

Complexity

  get    put    remove
  ------ ------ --------
  O(1)   O(1)   O(1)

Android: - Cache by ID - Session - Product lookup

------------------------------------------------------------------------

# HashMap Internals

    Key
     ↓
    hashCode()
     ↓
    Bucket
     ↓
    Linked List / Tree
     ↓
    Value

-   Default Capacity = 16
-   Load Factor = 0.75
-   Resize = ×2
-   Average O(1)
-   Worst O(n)

------------------------------------------------------------------------

# Iterators

``` kotlin
val iterator = list.iterator()

while(iterator.hasNext()){
    println(iterator.next())
}
```

Mutable iterator

``` kotlin
val iterator = list.mutableIterator()

while(iterator.hasNext()){
    if(iterator.next()==2){
        iterator.remove()
    }
}
```

ListIterator

-   previous()
-   next()
-   add()
-   set()

Use: - Safe removal while iterating

------------------------------------------------------------------------

# Ordering

Natural Ordering

``` kotlin
sorted()
sortedDescending()
```

Custom Ordering

``` kotlin
sortedBy {}
sortedByDescending {}
sortedWith()
compareBy()
thenBy()
reversed()
shuffled()
```

Comparable - compareTo() - Natural ordering

Comparator - compare() - Multiple custom orderings

------------------------------------------------------------------------

# Copy

Shallow Copy

-   Collection copied
-   Objects shared

Deep Copy

-   Collection copied
-   Objects copied

------------------------------------------------------------------------

# Filtering

## filter()

Keep matching elements

## filterNot()

Remove matching

## filterIndexed()

Filter with index

## filterNotNull()

Remove nulls

## filterIsInstance`<T>`{=html}()

Keep only one type

## partition()

Returns Pair\<List`<T>`{=html}, List`<T>`{=html}\>

## any()

At least one matches

## none()

No element matches

## all()

Every element matches

Android: - Active users - Deleted messages - Validation - Sealed classes

------------------------------------------------------------------------

# Transformations

## map()

One → One transformation

## mapIndexed()

Transform with index

## mapNotNull()

Transform + remove null

## associate()

Element → Pair

## associateBy()

Property → Object

## associateWith()

Object → Value

## flatten()

List\<List`<T>`{=html}\> → List`<T>`{=html}

## flatMap()

map() + flatten()

## zip()

Combine two collections by index

## unzip()

Reverse of zip

Android: - DTO → UI - Entity → Domain - Merge APIs - Cache maps

------------------------------------------------------------------------

# Grouping

## groupBy()

Returns

    Map<K, List<T>>

Use: - UI sections - Chat grouped by date

## groupingBy()

Lazy grouping

Works with

-   eachCount()
-   fold()
-   reduce()
-   aggregate()

### eachCount()

Count items

### fold()

Initial accumulator supplied

### reduce()

Uses first element

### aggregate()

Full control

------------------------------------------------------------------------

# Window Operations

## chunked()

Split into fixed-size chunks

``` kotlin
list.chunked(3)
```

Example: Pagination

## windowed()

Sliding windows

``` kotlin
list.windowed(3)
```

Example: Moving average

------------------------------------------------------------------------

# Plus / Minus

``` kotlin
list + element
list - element
```

Returns new collection.

------------------------------------------------------------------------

# Sequences

Lazy processing

``` kotlin
list
    .asSequence()
    .filter { ... }
    .map { ... }
    .toList()
```

Use for large collections.

------------------------------------------------------------------------

# Complexity

  Function    Complexity
  ----------- -------------
  map         O(n)
  filter      O(n)
  groupBy     O(n)
  flatMap     O(n)
  flatten     O(n)
  zip         O(min(n,m))
  partition   O(n)

------------------------------------------------------------------------

# Android Interview Mapping

  Problem                API
  ---------------------- ---------------
  DTO → UI               map
  Remove null API data   filterNotNull
  Active users           filter
  Group chats            groupBy
  Count errors           eachCount
  Sum salaries           fold
  Highest salary         reduce
  Custom aggregation     aggregate
  Cache by ID            associateBy
  Merge parallel lists   zip
  Sliding analytics      windowed
  Pagination             chunked

------------------------------------------------------------------------

# Frequently Asked Interview Questions

-   List vs Set vs Map
-   HashMap internals
-   Comparable vs Comparator
-   map vs flatMap
-   groupBy vs groupingBy
-   fold vs reduce vs aggregate
-   filter vs filterNot
-   partition vs filter
-   zip vs map
-   Sequence vs List
-   Shallow vs Deep Copy

------------------------------------------------------------------------

# Quick Memory Tricks

-   map → Transform
-   filter → Keep
-   filterNot → Remove
-   flatMap = map + flatten
-   groupBy → Create groups
-   groupingBy → Aggregate groups
-   fold → Initial value
-   reduce → First element
-   aggregate → Full control
-   zip → Join by index
-   partition → Split into two
-   chunked → Fixed groups
-   windowed → Sliding groups
-   associateBy → Property → Object
-   associateWith → Object → Value
