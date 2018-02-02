package pq.khanh.vn.yournearby.ui.example

import io.reactivex.subjects.BehaviorSubject
import pq.khanh.vn.yournearby.model.Book

/**
 * Created by khanhpq on 1/31/18.
 */
class OutInClass<out T>(val name: T) {
//    fun setItem(item : T){
//        this.name = item
//    }

    fun getItem(): T = name

    fun behaviourSubject() {
        val subject = BehaviorSubject.create<Book>()
        for(i in 5 downTo 1) print(i)
    }

    interface Navigable {

        val onNavigationClick: (() -> Unit)?
    }
}