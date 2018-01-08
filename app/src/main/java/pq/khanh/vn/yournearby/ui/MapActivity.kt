package pq.khanh.vn.yournearby.ui

import android.Manifest
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import pq.khanh.vn.yournearby.R
import pq.khanh.vn.yournearby.extensions.d

@RuntimePermissions
class MapActivity : AppCompatActivity() , OnMapReadyCallback {
    private lateinit var coord : LatLng
    private var googleMap : GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        d("on create")
        setContentView(R.layout.activity_map)
        coord = intent?.getParcelableExtra("location_coord") ?: LatLng(0.0, 0.0)
        d("${coord.latitude}, ${coord.longitude}")
        val supportMap = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMap.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        d("onMap ready")
        googleMap = map
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        showLocationOnMapWithPermissionCheck(googleMap!!)
    }
    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showLocationOnMap(map: GoogleMap) {
        val sydney = LatLng(-33.852, 151.211)
        map.addMarker(MarkerOptions().position(sydney)
                .title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onDestroy() {
        super.onDestroy()
        googleMap?.clear()
    }
}
