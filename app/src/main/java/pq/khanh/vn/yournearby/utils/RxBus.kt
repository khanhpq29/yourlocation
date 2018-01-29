package pq.khanh.vn.yournearby.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxBus {
    private val bus: PublishSubject<Any> = PublishSubject.create()

    fun send(event: Any) = bus.onNext(event)

    fun toObservable(): Observable<Any> = bus

    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }
}