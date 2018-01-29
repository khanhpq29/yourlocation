package pq.khanh.vn.yournearby.ui.items

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_items.*
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.intentFor
import pq.khanh.vn.yournearby.model.Book
import pq.khanh.vn.yournearby.ui.add.AddActivity
import pq.khanh.vn.yournearby.ui.order.OrderActivity
import pq.khanh.vn.yournearby.utils.RxBus

class ItemsActivity : AppCompatActivity(), ItemBookView {
    private lateinit var books: MutableList<Book>
    private lateinit var bookAdapter: BookAdapter
    private lateinit var realm: Realm
    private lateinit var presenter: ItemsPresenter
    private var bookPosition : Int = 0
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = ItemsPresenter(this)
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        books = presenter.getAll(realm)
        bookAdapter = BookAdapter(books, onClick = { book: Book ->
            if (book.total > 0) {
                val intent = intentFor<OrderActivity>(this)
                intent.putExtra("item", book)
                startActivity(intent)
            }
        }, onDelete = { book: Book ->
            AlertDialog.Builder(this)
                    .setTitle("Choose action")
                    .setItems(resources.getStringArray(R.array.action), { _, which ->
                        when (which) {
                            0 -> showDeleteDialog(book)
                            1 -> {
                                bookPosition = books.indexOf(book)
                                val intent = intentFor<AddActivity>(this)
                                intent.putExtra("book_data", book)
                                startActivityForResult(intent, 121)
                            }
                        }
                    })
                    .create()
                    .show()

        })

        listItems.apply {
            layoutManager = LinearLayoutManager(this@ItemsActivity)
            adapter = bookAdapter
            addItemDecoration(DividerItemDecoration(this@ItemsActivity, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
        compositeDisposable.add(
                RxBus.toObservable(Book::class.java)
                        .subscribeOn(Schedulers.io())
                        .subscribe {

                            //                            presenter.getAll(realm)
//                            bookAdapter.notifyDataSetChanged()
                        }

        )
    }

    private fun showDeleteDialog(book: Book) {
        AlertDialog.Builder(this)
                .setTitle("Delete this item")
                .setMessage("Are you sure?")
                .setPositiveButton("Ok", { _, _ -> presenter.deleteItem(realm, book) })
                .setNegativeButton("Cancel", { dialog, _ -> dialog.dismiss() })
                .create()
                .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == 120) {
                    val book = data.getParcelableExtra<Book>("book")
                    books.add(book)
                    bookAdapter.notifyDataSetChanged()
                }else if (requestCode == 121){
                    val book = data.getParcelableExtra<Book>("book")
                    books.removeAt(bookPosition)
                    books.add(bookPosition, book)
                    bookAdapter.notifyItemChanged(bookPosition)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun deleteSuccess(book: Book) {
        books.remove(book)
        bookAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        if (item?.itemId == R.id.m_add) {
            val intent = intentFor<AddActivity>(this)
            intent.putExtra("book_data", Book())
            startActivityForResult(intent, 120)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
        compositeDisposable.clear()
    }
}
