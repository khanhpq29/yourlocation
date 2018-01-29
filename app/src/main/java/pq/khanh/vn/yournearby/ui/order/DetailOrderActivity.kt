package pq.khanh.vn.yournearby.ui.order

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_detail_order.*
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.hide
import pq.khanh.vn.yournearby.extensions.show
import pq.khanh.vn.yournearby.model.Book
import pq.khanh.vn.yournearby.utils.RxBus
import pq.khanh.vn.yournearby.utils.pref.AppReference

class DetailOrderActivity : AppCompatActivity(), DetailView {
    private lateinit var presenter: DetailPresenter
    private lateinit var book: Book
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        presenter = DetailPresenter(this)
        initLayout()
        setupListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
    override fun afterBook() {
        presenter.updateItem(realm, book)
    }

    override fun updateItem(book: Book) {
        RxBus.send(book)
    }

    private fun initLayout() {
        val selectNumber = intent.getIntExtra("select_num", 0)
        if (selectNumber > 0) {
            tvNoData.hide(true)
            book = intent.getParcelableExtra("goods_name")
            tvGoodName.text = book.name
            tvTotalBook.text = selectNumber.toString()
        } else {
            tvNoData.show()
            tvGoodName.hide(true)
            tvTotalBook.hide(true)
            btnBook.hide(true)
        }
    }

    private fun setupListener() {
        btnBook.setOnClickListener {
            val number = tvTotalBook.text.toString().toInt()
            presenter.startOrder(this, book, number)
            intent.putExtra("book", book)
            AppReference.setRestNumber(this, book.total)
            setResult(Activity.RESULT_OK, intent)
            AppReference.setSelectNumber(this, 0)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
