package pq.khanh.vn.yournearby.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.d

class MapActivity : AppCompatActivity() , OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("on create")
        setContentView(R.layout.activity_map)
    }

    override fun onMapReady(map: GoogleMap?) {
        d("onMap ready")
    }
}
