package pq.khanh.vn.yournearby.ui.location

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import pq.khanh.vn.yournearby.R

import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.frContainer, LocationFragment()).commit()
    }

}
