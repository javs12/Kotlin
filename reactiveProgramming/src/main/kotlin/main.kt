import io.reactivex.rxjava3.core.Observable

fun main(args: Array<String>) {
    println("esto es una prueeba de kotlin ")
    exampleOf("just") {
        val observable: Observable<Int> = Observable.just(1)
    }
}