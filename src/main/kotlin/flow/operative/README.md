# Kotlin Flow operators cheat sheet

This folder contains small Kotlin Flow operator examples. The examples use
`runBlocking` only to make a standalone `main` function easy to run. In an
Android app, collect flows from a lifecycle-aware coroutine scope instead.

## Operator pipeline

```text
Flow source -> intermediate operators -> terminal operator
flowOf(...)     filter/map/debounce    collect(...)
```

Intermediate operators return a new `Flow` and do not start work by themselves.
`collect` is terminal: it starts collection and receives the values.

## 1. Transformation

Use these to change each emitted value.

| Operator | What it does | Example |
| --- | --- | --- |
| `map` | Converts one input value to one output value. | [MapExample.kt](transformation/MapExample.kt) |
| `transform` | Can emit zero, one, or many values for each input. | [Transform.kt](transformation/Transform.kt) |

```kotlin
flowOf(1, 2, 3).map { it * 10 } // 10, 20, 30
```

## 2. Filtering and limiting

Use these to keep, skip, or stop values.

| Operator | What it does | Example |
| --- | --- | --- |
| `filter` | Keeps values matching a condition. | [Filter.kt](filter/Filter.kt) |
| `drop(n)` | Skips the first `n` values. | [Drop.kt](filter/Drop.kt) |
| `dropWhile` | Skips values while its condition is true; then passes all later values. | [DropWhile.kt](filter/DropWhile.kt) |
| `take(n)` | Emits only the first `n` values, then cancels upstream collection. | [Take.kt](filter/Take.kt) |
| `takeWhile` | Emits while its condition is true; stops at the first non-matching value. | [TakeWhile.kt](filter/TakeWhile.kt) |
| `distinctUntilChanged` | Removes only consecutive duplicate values. | [DistinctUnitChanged.kt](filter/DistinctUnitChanged.kt) |

## 3. Combining operator chains

Operators are commonly chained to express a processing pipeline.

| Pattern | What it does | Example |
| --- | --- | --- |
| `filter().map()` | Keeps selected values, then transforms them. | [FilterAndMap.kt](allcombine/FilterAndMap.kt) |
| `drop().map()` | Skips initial values before transforming the remainder. | [MapAndDrop.kt](allcombine/MapAndDrop.kt) |

Order matters: `filter().map()` maps only retained values, while `map().filter()` maps every input before filtering.

## 4. Flattening flows

Use these when each upstream value creates another `Flow`.

| Operator | Behaviour | Example |
| --- | --- | --- |
| `flatMapConcat` | Collects inner flows one at a time, in source order. | [FlatMapConcate.kt](flatten/FlatMapConcate.kt) |
| `flatMapMerge` | Collects inner flows concurrently; result order can interleave. | [FlatMapMerge.kt](flatten/FlatMapMerge.kt) |
| `flatMapLatest` | Cancels the previous inner flow when a new source value arrives. | [FlatMapLatest.kt](flatten/FlatMapLatest.kt) |

Choose `flatMapLatest` for changing searches or UI selections, `flatMapConcat` when order is essential, and `flatMapMerge` for independent concurrent work.

## 5. Combining multiple flows

| Operator | Behaviour | Example |
| --- | --- | --- |
| `zip` | Pairs values by position; it stops when the shorter flow finishes. | [Zip.kt](combination/Zip.kt) |
| `combine` | Emits whenever either flow updates, using the latest value from each flow. | [Combine.kt](combination/Combine.kt) |
| `merge` | Forwards values from all flows as they arrive, without pairing them. | [Merge.kt](combination/Merge.kt) |

```text
zip:     A, B, C   +   1, 2, 3  ->  A-1, B-2, C-3
combine: A, B      +   1, 2     ->  latest pairs whenever either side emits
merge:   A, B      +   1, 2     ->  A, 1, B, 2 (arrival order)
```

## 6. Buffering and cancellation

These help when producer and collector run at different speeds.

| Operator | Behaviour | Example |
| --- | --- | --- |
| `buffer` | Lets production and collection run in separate coroutines, up to buffer capacity. | [Buffer.kt](buffering/Buffer.kt) |
| `conflate` | When the collector is slow, drops intermediate values but keeps the latest one. | [Conflate.kt](buffering/Conflate.kt) |
| `collectLatest` | Cancels processing of the previous value when a newer value arrives. | [CollectLatest.kt](buffering/CollectLatest.kt) |

`conflate` drops values; `collectLatest` cancels the collector block. They are similar in effect but useful at different stages of a pipeline.

## 7. Time-based operators

| Operator | Behaviour | Example |
| --- | --- | --- |
| `debounce(timeout)` | Waits for a quiet period and emits the most recent value. Ideal for search input. | [Debounce.kt](time/base/Debounce.kt) |
| `sample(period)` | Periodically emits the latest value seen during each sampling window. | [Sample.kt](time/base/Sample.kt) |

For the `Debounce.kt` example, rapid `"A"`, `"An"`, `"Ana"`, and `"Anand"` emissions produce only `"Anand"` after 300 ms of inactivity. In the second example, `3` is emitted after the pause and `4` is emitted when the flow completes.

## 8. Lifecycle, completion, and errors

| Operator | Behaviour | Example |
| --- | --- | --- |
| `onEach` | Performs a side effect for each value without changing it. | [OnEachExample.kt](lifecycle/operators/OnEachExample.kt) |
| `catch` | Handles exceptions from upstream operators. | [Catch.kt](error/handlin/Catch.kt) |
| `onCompletion` | Runs when collection completes, fails, or is cancelled. | [OnCompletion.kt](error/handlin/OnCompletion.kt) |
| `retry` | Re-collects a failing upstream flow a fixed number of times. | [Retry.kt](error/handlin/Retry.kt) |
| `retryWhen` | Re-collects conditionally using the exception and retry attempt number. | [RetryWhen.kt](error/handlin/RetryWhen.kt) |

Place `catch` after the operators whose errors it should handle. It does not catch exceptions thrown inside the downstream `collect` block. Use `retryWhen` when retries depend on the exception type, attempt number, or a delay/backoff policy.

## Quick selection guide

| Need | Use |
| --- | --- |
| Convert every value | `map` |
| Produce multiple values from one input | `transform` |
| Keep only matching values | `filter` |
| Combine corresponding positions | `zip` |
| React to the latest values from two flows | `combine` |
| Run independent inner flows concurrently | `flatMapMerge` |
| Cancel obsolete work | `flatMapLatest` or `collectLatest` |
| Wait until typing stops | `debounce` |
| Avoid a slow collector blocking the producer | `buffer` or `conflate` |
| Recover from an upstream failure | `catch`, `retry`, or `retryWhen` |
