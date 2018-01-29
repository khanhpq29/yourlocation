package pq.khanh.vn.yournearby.ui.order

import android.content.Context
import io.realm.Realm
import pq.khanh.vn.yournearby.model.Book
import pq.khanh.vn.yournearby.utils.pref.AppReference

/**
 * Created by khanhpq on 1/25/18.
 */
class DetailPresenter(val view : DetailView) {
    fun startOrder(context: Context, book : Book, number : Int){
        AppReference.setRestNumber(context, number)
        book.total = book.total - number
        view.afterBook()
    }

    fun updateItem(realm : Realm, book: Book){
        realm.executeTransaction { realmObj ->
            realmObj.copyToRealmOrUpdate(book)
        }
        view.updateItem(book)
    }
}