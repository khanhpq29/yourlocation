package pq.khanh.vn.yournearby.ui.add

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add.*
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.model.Book

class AddActivity : AppCompatActivity(), AddView {
    override fun addSuccess() {
        edtTitle.setText("")
        edtTotal.setText("")
        intent.putExtra("book", book)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun updateSuccess() {
        val total = edtTotal.text.toString().toInt()
        val title = edtTitle.text.toString()
        book.total = total
        book.name = title
        intent.putExtra("book", book)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private lateinit var realm: Realm
    private lateinit var presenter: AddPresenter
    private lateinit var book: Book
    private var isUpdate = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        initData()
        presenter = AddPresenter(this)
        addNewItem()
    }

    private fun initData() {
        book = intent.getParcelableExtra("book_data")
        if (book != Book()) {
            btnAdd.text = "Update"
            edtTitle.setText(book.name)
            edtTotal.setText("${book.total}")
            isUpdate = true
        } else {
            btnAdd.text = "Add"
            isUpdate = false
        }
    }

    private fun addNewItem() {
        btnAdd.setOnClickListener {
            if (!isUpdate) {
                val total = edtTotal.text.toString().toInt()
                val title = edtTitle.text.toString()
                book = Book(System.currentTimeMillis(), total, title)
                presenter.insertItem(realm, book)
            } else {
                presenter.updateItem(realm, book)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        realm.close()
    }
}
