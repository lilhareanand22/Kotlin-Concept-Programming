package design.pattern.behavioral

import kotlin.properties.Delegates

/**
 * ============================================================================
 * DESIGN PATTERN: OBSERVER (Behavioral)
 * ============================================================================
 * 
 * Intent:
 * Define a one-to-many dependency between objects so that when one object 
 * changes state, all its dependents are notified and updated automatically.
 * 
 * Real-World Analogy:
 * A YouTube Channel or Newsletter Subscription:
 * When a creator uploads a new video, the platform notifies all subscribed users.
 * Subscribers can join (subscribe) or leave (unsubscribe) at any time.
 * 
 * In Kotlin:
 * 1. Classic Subject-Observer Pattern: Subject manages a list of observers.
 * 2. Idiomatic Kotlin Observer: Using `Delegates.observable` from kotlin.properties,
 *    and higher-order functions / lambdas as lightweight listeners.
 */

// ----------------------------------------------------------------------------
// 1. CLASSIC OBSERVER PATTERN INTERFACES
// ----------------------------------------------------------------------------

interface Observer<T> {
    fun onUpdate(data: T)
}

interface Subject<T> {
    fun subscribe(observer: Observer<T>)
    fun unsubscribe(observer: Observer<T>)
    fun notifyObservers(data: T)
}

// ----------------------------------------------------------------------------
// 2. CONCRETE SUBJECT: NEWS AGENCY / YOUTUBE CHANNEL
// ----------------------------------------------------------------------------

data class NewsArticle(val title: String, val category: String)

class NewsAgency : Subject<NewsArticle> {
    private val subscribers = mutableListOf<Observer<NewsArticle>>()

    override fun subscribe(observer: Observer<NewsArticle>) {
        subscribers.add(observer)
        println("-> [NewsAgency] New subscriber added. Total subscribers: ${subscribers.size}")
    }

    override fun unsubscribe(observer: Observer<NewsArticle>) {
        subscribers.remove(observer)
        println("-> [NewsAgency] Subscriber removed. Total subscribers: ${subscribers.size}")
    }

    override fun notifyObservers(data: NewsArticle) {
        println("\n>> [NewsAgency] Broadcasting News: '${data.title}' to ${subscribers.size} subscribers...")
        subscribers.forEach { it.onUpdate(data) }
    }

    fun publishNews(title: String, category: String) {
        val article = NewsArticle(title, category)
        notifyObservers(article)
    }
}

// ----------------------------------------------------------------------------
// 3. CONCRETE OBSERVERS
// ----------------------------------------------------------------------------

class EmailSubscriber(private val email: String) : Observer<NewsArticle> {
    override fun onUpdate(data: NewsArticle) {
        println("   [Email to $email] New '${data.category}' article: ${data.title}")
    }
}

class MobileAppSubscriber(private val deviceId: String) : Observer<NewsArticle> {
    override fun onUpdate(data: NewsArticle) {
        println("   [Push to $deviceId] Breaking News: ${data.title}")
    }
}

// ----------------------------------------------------------------------------
// 4. IDIOMATIC KOTLIN: `Delegates.observable`
// ----------------------------------------------------------------------------
// For observing individual property changes without full Subject boilerplate:
class StockTicker(val symbol: String) {
    // Delegates.observable invokes the callback whenever the property is modified
    var price: Double by Delegates.observable(0.0) { property, oldPrice, newPrice ->
        val change = newPrice - oldPrice
        val direction = if (change >= 0) "▲ +$change" else "▼ $change"
        println("[StockTicker: $symbol] Price updated: \$$oldPrice -> \$$newPrice ($direction)")
    }
}

// ----------------------------------------------------------------------------
// RUNNABLE DEMO
// ----------------------------------------------------------------------------
fun main() {
    println("=== 1. DEMONSTRATING CLASSIC OBSERVER PATTERN ===")

    val bbcNews = NewsAgency()

    val alice = EmailSubscriber("alice@gmail.com")
    val bob = EmailSubscriber("bob@yahoo.com")
    val charlieApp = MobileAppSubscriber("Pixel_8_Pro_#9872")

    // Subscribe observers
    bbcNews.subscribe(alice)
    bbcNews.subscribe(bob)
    bbcNews.subscribe(charlieApp)

    // Publish event
    bbcNews.publishNews("Kotlin 2.2 Released with High-Performance Compiler", "Tech")

    // Unsubscribe an observer
    bbcNews.unsubscribe(bob)

    // Publish another event
    bbcNews.publishNews("Global Space Exploration Summit Announced", "Science")

    println("\n=== 2. DEMONSTRATING KOTLIN 'Delegates.observable' ===")

    val googleStock = StockTicker("GOOGL")
    googleStock.price = 175.50
    googleStock.price = 182.20
    googleStock.price = 179.90
}
