package pq.khanh.vn.yournearby.ui.add

import io.realm.Realm
import pq.khanh.vn.yournearby.model.Book

/**
 * Created by khanhpq on 1/26/18.
 */
class AddPresenter(val view : AddView){
    fun insertItem(realm : Realm, book : Book){
        realm.beginTransaction()
        realm.insertOrUpdate(book)
        realm.commitTransaction()
        view.addSuccess()
    }

    fun updateItem(realm : Realm, book: Book){
        realm.beginTransaction()
        realm.insertOrUpdate(book)
        realm.commitTransaction()
        view.updateSuccess()
    }
}