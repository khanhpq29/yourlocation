package pq.khanh.vn.yournearby.ui.items

import io.realm.Realm
import pq.khanh.vn.yournearby.model.Book

/**
 * Created by khanhpq on 1/29/18.
 */
class ItemsPresenter(val view : ItemBookView) {
    fun deleteItem(realm : Realm, book: Book){
        realm.executeTransaction {
            val realmQuery = realm.where(Book::class.java)
            realmQuery.equalTo("id", book.id)
            val realmAlarm = realmQuery.findAll()
            if (realmAlarm.size > 0) {
                realmAlarm.deleteFromRealm(0)
            }
        }
        view.deleteSuccess(book)
    }

    fun getAll(realm: Realm) : MutableList<Book>{
        var books : MutableList<Book> = mutableListOf()
        realm.executeTransaction { realmQuery: Realm ->
            val bookResult = realmQuery.where(Book::class.java).findAll()
            books = realm.copyFromRealm(bookResult)
        }
        return books
    }
}