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
import pq.khanh.vn.yournearby.extensions.e
import pq.khanh.vn.yournearby.utils.pref.AppReference

class DemoActivity : AppCompatActivity() {
    private lateinit var adapter: LikeHoodAdapter
    private var list: MutableList<String> = mutableListOf()
    private var isList = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(R.layout.activity_demo)
        initData()
        d("size ${list.size}")
        initView()
        e("${AppReference.getDisplayType(this)}")
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        adapter = LikeHoodAdapter(list)
        rclExample.layoutManager = LinearLayoutManager(this)
        rclExample.adapter = adapter
        rclExample.setHasFixedSize(true)
        rclExample.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
//        rclExample.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_switch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        isList = AppReference.getDisplayType(this)
//        var isSwitch = adapter.switchLayout()
//        e("${AppReference.getDisplayType(this)}")
        isList = adapter.switchLayout()
        if (isList) {
            menu?.findItem(R.id.menu_switch)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_list)
            adapter.switchLayout()
        } else {
            menu?.findItem(R.id.menu_switch)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_grid)
            adapter.switchLayout()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_switch) {
            invalidateOptionsMenu()
            isList = adapter.switchLayout()
            if (isList) {
                rclExample.layoutManager = LinearLayoutManager(this)
            } else {
                rclExample.layoutManager = GridLayoutManager(this, 2)
            }
            AppReference.setDisplayType(this, isList)
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
        list.add("android")
        list.add("android")
        list.add("android")
        list.add("android")
        list.add("android")
    }
}
