package pq.khanh.vn.yournearby

import pq.khanh.vn.yournearby.model.Book

/**
 * Created by khanhpq on 1/25/18.
 */
object Data {
    fun createBook() : Book{
        val book = Book(10, "book1")
        return book
    }
}