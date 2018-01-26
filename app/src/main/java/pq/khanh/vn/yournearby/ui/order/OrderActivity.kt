package pq.khanh.vn.yournearby.ui.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_detail_order.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import pq.khanh.vn.yournearby.Data
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.d
import pq.khanh.vn.yournearby.extensions.intentFor
import pq.khanh.vn.yournearby.extensions.showToast
import pq.khanh.vn.yournearby.model.Book
import pq.khanh.vn.yournearby.utils.pref.AppReference

class OrderActivity : AppCompatActivity(), OrderView {
    private lateinit var book: Book
    private lateinit var presenter: OrderPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        book = Data.createBook()
        presenter = OrderPresenter(this)
        showToast("${AppReference.getSelectNumber(this)}")
        initData()
        setupListener()
    }

    override fun increase() {
    }

    override fun decreaseNumber() {
    }

    override fun onResume() {
        super.onResume()
        d("${AppReference.getRestNumber(this)}")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_menu, menu)
        val menuItem = menu?.findItem(R.id.m_cart)
        val menuView = menuItem?.actionView
        val textBadge = menuView?.findViewById<FrameLayout>(R.id.badge_container)
        textBadge?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == 200) {
                    val book = data.getParcelableExtra<Book>("book")
                    tvTotal.text = book.total.toString()
                    tvBadge.text = "0"
                    tvBoughtNumber.text = "1"
                }
            }
        }
    }

    private fun initData() {
        tvTotal.text = AppReference.getRestNumber(this).toString()
        tvName.text = book.name
    }

    private fun setupListener() {
        imgAdd.setOnClickListener {
            val no = tvBoughtNumber.text.toString().toInt()
            if (no < book.total) {
                tvBoughtNumber.text = "${no + 1}"
            }

            imgMinus.setOnClickListener {
                val no = tvBoughtNumber.text.toString().toInt()
                if (no > 1) {
                    tvBoughtNumber.text = "${no - 1}"
                }
            }

            btnSelect.setOnClickListener {
                tvBadge.text = tvBoughtNumber.text.toString()
                val selectedNo = tvBadge.text.toString().toInt()
                AppReference.setSelectNumber(this, selectedNo)
            }

            badge_container.setOnClickListener{
                val intent = intentFor<DetailOrderActivity>(this)
                intent.putExtra("goods_name", book)
                if (tvBadge.text.toString().isBlank()) {
                    intent.putExtra("select_num", 0)
                } else {
                    intent.putExtra("select_num", tvBadge.text.toString().toInt())
                }
                startActivityForResult(intent, 200)
            }

            icBack.setOnClickListener {
                finish()
            }
        }
    }

}
