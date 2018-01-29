package pq.khanh.vn.yournearby.ui.items

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_book.view.*
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.hide
import pq.khanh.vn.yournearby.extensions.inflateLayout
import pq.khanh.vn.yournearby.extensions.show
import pq.khanh.vn.yournearby.model.Book

/**
 * Created by khanhpq on 1/26/18.
 */
class BookAdapter(private val bookList: MutableList<Book>, private val onClick: (Book) -> Unit, private val onDelete: (Book) -> Unit) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BookViewHolder {
        val view = parent.inflateLayout(R.layout.item_book)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder?, position: Int) {
        val book = bookList[position]
        holder?.bind(book)
        holder?.itemView?.setOnClickListener { onClick(book) }
        holder?.itemView?.setOnLongClickListener {
            onDelete(book)
            false
        }
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(book: Book) {
            itemView.tvTitle.text = book.name
            itemView.tvAmount.text = book.total.toString()
            if (book.total > 0) {
                itemView.tvAlert.hide(true)
            }else {
                itemView.tvAlert.show()
                itemView.tvAlert.text = "finish"
            }
        }
    }
}