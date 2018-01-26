package pq.khanh.vn.yournearby.ui.example

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_demo.*
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.adapter.LikeHoodAdapter
import pq.khanh.vn.yournearby.extensions.d
import pq.khanh.vn.yournearby.extensions.showToast
import pq.khanh.vn.yournearby.utils.pref.AppReference

class DemoActivity : AppCompatActivity() {
    private lateinit var adapter: LikeHoodAdapter
    private var list: MutableList<String> = mutableListOf()
    private var isSwitch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(R.layout.activity_demo)
        initData()
        initView()
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = LikeHoodAdapter(list, {position -> showToast("$position")})
        rclExample.layoutManager = LinearLayoutManager(this@DemoActivity)
        rclExample.adapter = adapter
        rclExample.setHasFixedSize(true)
        rclExample.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_switch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        showToast("${AppReference.getDisplayType(this)}")
        isSwitch = adapter.switchLayout()
        if (isSwitch) {
            menu?.findItem(R.id.menu_switch)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_list)
            menu?.findItem(R.id.menu_switch)?.title = "Switch to list view"
            adapter.switchLayout()
        } else {
            menu?.findItem(R.id.menu_switch)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid)
            menu?.findItem(R.id.menu_switch)?.title = "Switch to grid view"
            adapter.switchLayout()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_switch) {
            invalidateOptionsMenu()
            isSwitch = adapter.switchLayout()
            if (isSwitch) {
                rclExample.layoutManager = LinearLayoutManager(this)
            } else {
                rclExample.layoutManager = GridLayoutManager(this, 2)
            }
            AppReference.setDisplayType(this, isSwitch)
            adapter.notifyDataSetChanged()
            return true
        }
        if (item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        d("stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("destroy")
    }

    private fun initData() {
        list.add("android")
        list.add("android")
        list.add("android")
        list.add("android")
        list.add("android")
        list.add("android")
        list.add("android")
    }

    private inline fun <reified T> checkType(number : Any){
        if (number is T){
            d("$number is true")
        }else {
            d("$number is false")
        }
    }
}
