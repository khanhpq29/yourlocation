package pq.khanh.vn.yournearby

import pq.khanh.vn.yournearby.model.Book

/**
 * Created by khanhpq on 1/25/18.
 */
object Data {
    fun createBook() : MutableList<Book>{
        val listBook = mutableListOf<Book>()
        listBook.add(Book(1, 10, "book1"))
        listBook.add(Book(2, 6, "book2"))
        listBook.add(Book(3, 8, "book4"))
        listBook.add(Book(4, 3, "book5"))
        listBook.add(Book(5, 1, "book6"))
        return listBook
    }

    fun creatABook() : Book{
        return Book(9, 10, "book1")
    }
}