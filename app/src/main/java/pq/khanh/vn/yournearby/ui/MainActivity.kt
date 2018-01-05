package pq.khanh.vn.yournearby.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.ui.location.LocationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.container, LocationFragment()).commit()
    }
}
