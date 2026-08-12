# Kotlin Coroutines Interview Master Guide

Welcome to my personal Kotlin Coroutines interview preparation guide. This document summarizes all the core concepts, code patterns, and expert interview answers from beginner to advanced levels.

---

## Table of Contents
1. [Phase 1: Fundamentals & Core Concepts](#phase-1-fundamentals--core-concepts)
2. [Phase 2: Dispatchers & Context](#phase-2-dispatchers--context)
3. [Phase 3: Cancellation & Exceptions](#phase-3-cancellation--exceptions)
4. [Phase 4: Advanced Flow & Synchronization](#phase-4-advanced-flow--synchronization)

---

## Phase 1: Fundamentals & Core Concepts

### Q1: What is a coroutine, and why is it called a "lightweight thread"?
> **Answer:** A coroutine is an instance of suspendable computation—it's a way to write asynchronous, non-blocking code. It's often called a "lightweight thread" because multiple coroutines can run on a single thread, and suspending a coroutine doesn't block the thread it's running on. Unlike OS threads which cost about 1MB of memory and have heavy context-switching costs, coroutines are managed entirely in user-space by the Kotlin runtime, allowing you to run hundreds of thousands of them concurrently without crashing your app.

### Q2: What is the difference between blocking a thread and suspending a coroutine?
> **Answer:** When a thread is **blocked**, it is completely occupied and idle; the OS cannot use it for anything else until the blocking operation finishes. When a coroutine is **suspended**, it pauses its execution and detaches from the underlying thread. The thread is immediately freed up to execute other coroutines or tasks. Once the asynchronous result is ready, the coroutine resumes its execution.

### Q3: What is the difference between `launch` and `async`?
> **Answer:** `launch` and `async` are both coroutine builders, but they serve different purposes. `launch` is used for "fire-and-forget" tasks; it returns a `Job` and doesn't return a result to the caller. `async` is used when you need a result back; it returns a `Deferred<T>` object, allowing you to fetch the result later using the `.await()` function. `async` is typically used when you want to execute multiple independent network or database calls concurrently to save time.

### Q4: What is `runBlocking`, and where should you use it?
> **Answer:** `runBlocking` creates a coroutine that blocks the current thread until its execution finishes. It acts as a bridge between blocking code and non-blocking coroutines. Because it blocks the thread it runs on, it should **never** be used in production UI code or on the Main thread, as it will cause UI freezes or ANRs. It is designed primarily for writing unit tests or running the `main()` function in console applications.

### Q5: What is a suspend function, and how does it differ from a regular function?
> **Answer:** A suspend function is a function that can be paused and resumed later without blocking the underlying thread. Unlike regular functions which execute sequentially and block the calling thread until they return, a suspend function can pause its execution when it hits a long-running operation (like a network call), freeing up the thread. Once the operation completes, it resumes its execution. Additionally, suspend functions can only be called from a coroutine or another suspend function.

### Q6: How do coroutines work under the hood? (Explain CPS and State Machines)
> **Answer:** Under the hood, Kotlin uses **Continuation-Passing Style (CPS)** and **State Machines**. When you mark a function as `suspend`, the Kotlin compiler rewrites the function, adding a hidden `Continuation` parameter to it. Every suspension point acts as a state in a state machine. When the function suspends, it saves its local variables and state inside the `Continuation` object and returns control to the caller. When the async task finishes, it invokes the continuation's `resumeWith()` function, triggering the next state of the state machine.

---

## Phase 2: Dispatchers & Context

### Q7: What is the difference between `Dispatchers.IO` and `Dispatchers.Default`?
> **Answer:** `Dispatchers.IO` is designed for blocking I/O operations, such as network requests, disk reading/writing, or database queries. It uses an expansive shared thread pool that can scale up to 64 threads or more if needed. `Dispatchers.Default`, on the other hand, is optimized for CPU-intensive tasks like heavy computation, sorting, or JSON parsing. Its thread pool is strictly limited to the number of CPU cores available to prevent CPU thrashing from context switching.

### Q8: What is `withContext`, and why should you use it instead of `launch` with a new dispatcher?
> **Answer:** `withContext` is a suspending function that allows you to switch the `CoroutineContext` (specifically the dispatcher) within an ongoing coroutine without launching a brand new coroutine. It suspends the current block, switches to the specified dispatcher to execute the block, and then automatically switches back to the original dispatcher when finished. It is preferred over launching a new coroutine because it keeps code sequential, avoids unnecessary nesting, and makes it easy to return a result directly to the caller.

---

## Phase 3: Cancellation & Exceptions

### Q9: Does `job.cancel()` immediately stop a coroutine?
> **Answer:** No, it doesn't. Cancellation in Kotlin Coroutines is **cooperative**. Calling `cancel()` on a `Job` just signals the coroutine to stop by changing its internal `isActive` state to `false`. The coroutine is responsible for checking this state. If the coroutine is performing a long-running computation without any suspension points, it will continue to run until it finishes. To make it cancelable, you must periodically call suspending functions like `yield()` or check `ensureActive()` or `isActive` manually.

### Q10: What is the difference between `withTimeout` and `withTimeoutOrNull`?
> **Answer:** Both are used to limit the execution time of a coroutine. `withTimeout` throws a `TimeoutCancellationException` if the time limit is exceeded, which you would need to catch with a `try-catch` block. `withTimeoutOrNull` is more idiomatic for many use cases; it returns the result of the block if it finishes in time, or returns `null` if it times out, avoiding the need for exception handling.

### Q11: How do exceptions propagate in Coroutines, and how do you handle them?
> **Answer:** In structured concurrency, exceptions propagate up from child to parent. If a child coroutine launched with `launch` throws an exception, it cancels its parent and all sibling coroutines. To handle or isolate failures, we use a `SupervisorJob` or `supervisorScope`, which stops exceptions from propagating upward, ensuring that the failure of one child doesn't affect its siblings. For `async`, exceptions are caught inside the `Deferred` object and are only thrown when `.await()` is called.

### Q12: What is the difference between `CoroutineExceptionHandler` and a `try-catch` block?
> **Answer:** A `try-catch` block works locally inside a coroutine for suspending calls. However, `try-catch` will **not** catch exceptions thrown inside a child `launch` coroutine because `launch` handles exceptions asynchronously and propagates them to the parent scope. A `CoroutineExceptionHandler` acts as a generic fallback mechanism for unhandled exceptions in root coroutines, but it only works with `launch`, not `async`.

---

## Phase 4: Advanced Flow & Synchronization

### Q13: What is the difference between StateFlow and SharedFlow?
> **Answer:** `StateFlow` is a specialized, narrow implementation of `SharedFlow` designed specifically to represent state. It requires an initial value, always holds a single current value, conflates emissions so collectors only receive the latest state, and ignores duplicate consecutive values. `SharedFlow`, on the other hand, is a general-purpose stream. It doesn't require an initial value, doesn't retain state by default unless a replay cache is configured, and is typically used for broadcasting one-time events like UI toasts or navigation actions.

### Q14: Why is StateFlow preferred over LiveData in modern Android architecture?
> **Answer:** StateFlow is a native Kotlin coroutines primitive, meaning it is multiplatform and can be used safely in domain and data layers, whereas LiveData is tied strictly to the Android lifecycle and the Android framework. StateFlow also supports powerful coroutine operators like `map`, `filter`, `combine`, and `flatMapLatest`, providing a robust reactive programming model.

### Q15: How do you handle shared mutable state safely in Kotlin Coroutines?
> **Answer:** To handle shared mutable state safely without causing race conditions, we avoid using traditional Java locks like `synchronized`, because they block the underlying thread. Instead, Kotlin provides a non-blocking primitive called **`Mutex`**. Using `mutex.withLock`, if a coroutine tries to access a critical section that is already locked, it **suspends** rather than blocks, freeing up the underlying thread. Alternative approaches include thread confinement using a single-threaded dispatcher, or using atomic types/flows depending on the use case.

### Q16: Why should you avoid Java's `synchronized` keyword inside coroutines?
> **Answer:** Java's `synchronized` block blocks the physical OS thread until the lock is released. If you use it inside a coroutine running on a shared thread pool (like `Dispatchers.IO` or `Default`), you end up blocking that thread entirely, preventing other coroutines from executing on it and potentially causing thread starvation. Coroutine primitives like `Mutex` are designed to suspend the coroutine instead of blocking the thread.