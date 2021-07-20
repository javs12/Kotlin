import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlin.math.pow
import kotlin.math.roundToInt

fun main(args: Array<String>) {
    exampleOf("just") {
        val observable = Observable.just(1, 2, 3)
    }

    exampleOf("fromIterable") {
        val observable: Observable<Int> =
                Observable.fromIterable(listOf(1, 2, 3))
    }

    exampleOf("subscribe") {
        val observable = Observable.just(1, 2, 3)
        observable.subscribe { println(it) }
    }

    exampleOf("empty") {
        val observable = Observable.empty<Unit>()
        observable.subscribeBy(
                onNext = { println(it) },
                onComplete = { println("Completed") }
        )
    }

    exampleOf("never") {
        val observable = Observable.never<Any>()
        observable.subscribeBy(
                onNext = { println(it) },
                onComplete = { println("Completed") }
        )
    }

    exampleOf("range") {
        val observable: Observable<Int> = Observable.range(1, 10)
        observable.subscribe {
            val n = it.toDouble()
            val fibonacci = ((1.61803.pow(n) - 0.61803.pow(n)) / 2.23606).roundToInt()
            println(fibonacci)
        }
    }

    exampleOf("dispose") {
        val mostPopular: Observable<String> = Observable.just("A", "B", "C")
        val subscription = mostPopular.subscribe {
            println(it)
        }
        subscription.dispose()
    }

    exampleOf("compositeDisposable") {
        val subscriptions = CompositeDisposable()
        val disposable = Observable.just("A", "B", "C")
                .subscribe {
                    println(it)
                }
        subscriptions.add(disposable)
        subscriptions.dispose()
    }

    exampleOf("create") {
        val disposables = CompositeDisposable()

        Observable.create<String> { emitter ->
            emitter.onNext("1")
            emitter.onComplete()
            emitter.onNext("?")
        }.subscribeBy(
                onNext = { println(it) },
                onComplete = { println("Completed") },
                onError = { println(it) }
        )
    }

    exampleOf("defer") {
        val disposables = CompositeDisposable()
        var flip = false
        val factory: Observable<Int> = Observable.defer {
            flip = !flip
            println(flip)
            if (flip) {
                Observable.just(1, 2, 3)
            } else {
                Observable.just(4, 5, 6)
            }
        }
        for (i in 0..3) {
            disposables.add(
                    factory.subscribe {
                        println(it)
                    }
            )
        }
    }
}